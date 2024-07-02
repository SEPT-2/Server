package example.com.plugins

import example.com.api.question
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*


fun Application.configureRouting() {
  install(Routing)
  install(Resources)


  routing {
    question()
  }
}
