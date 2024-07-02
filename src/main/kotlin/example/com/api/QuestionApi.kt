package example.com.api

import io.github.smiley4.ktorswaggerui.dsl.resources.get
import io.ktor.resources.*
import io.ktor.server.routing.*

@Resource("/questions")
class QuestionApi {

  @Resource("/fetch")
  class Fetch
}

fun Route.question() {

  get<QuestionApi>({}) {}

  get<QuestionApi.Fetch>({}) {}
}