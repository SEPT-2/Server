package example.com.plugins

import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

fun Application.configureKoin() {
  install(Koin) {

    val repository = module { }

    val service = module { }

    modules(repository, service)
  }
}