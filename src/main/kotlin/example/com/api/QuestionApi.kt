package example.com.api

import example.com.service.QuestionService
import io.github.smiley4.ktorswaggerui.dsl.resources.get
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

@Resource("/questions")
class QuestionApi {

  @Resource("/fetch")
  class Fetch(val parent: QuestionApi = QuestionApi())
}

fun Route.question() {
  val questionService = QuestionService()

  get<QuestionApi>({}) {
    val allQuestion = questionService.getAllQuestion()

    call.respond(allQuestion)
  }

  get<QuestionApi.Fetch>({}) {
    questionService.fetchQuestionFromOpenAI()

    call.respondText("Question fetched")
  }
}