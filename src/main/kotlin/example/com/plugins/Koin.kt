package example.com.plugins

import example.com.repository.AnswerRepository
import example.com.repository.QuestionRepository
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

fun Application.configureKoin() {
  install(Koin) {

    val repository = module {
      single { QuestionRepository() }
      single { AnswerRepository() }

    }

    val service = module { }

    modules(repository, service)
  }
}