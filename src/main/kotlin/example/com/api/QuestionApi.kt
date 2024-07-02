package example.com.api

import example.com.domain.Question
import example.com.service.QuestionService
import io.github.smiley4.ktorswaggerui.dsl.OpenApiRoute
import io.github.smiley4.ktorswaggerui.dsl.resources.get
import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

@Resource("/questions")
class QuestionApi {

  companion object {
    val get: OpenApiRoute.() -> Unit = {
      summary = "질문 목록 조회"
      description = "질문 목록을 조회합니다."
      tags = listOf("질문")

      response {
        HttpStatusCode.OK to {
          description = "질문 생성 성공"
          body<List<Question.Response>>()
        }
      }
    }
  }

  @Resource("/fetch")
  class Fetch(val parent: QuestionApi = QuestionApi()) {
    companion object {
      val get: OpenApiRoute.() -> Unit = {
        summary = "질문 생성하기"
        description = "질문을 생성하도록 요청합니다."
        tags = listOf("질문")
      }
    }
  }
}

fun Route.question() {
  val questionService = QuestionService()

  get<QuestionApi>(QuestionApi.get) {
    val allQuestion = questionService.getAllQuestion()

    call.respond(allQuestion)
  }

  get<QuestionApi.Fetch>(QuestionApi.Fetch.get) {
    questionService.fetchQuestionFromOpenAI()

    call.respondText("Question fetched")
  }
}