package example.com

import example.com.plugins.*
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
  EngineMain.main(args)
}

fun Application.module() {
  DatabaseFactory.init()

  configureKoin()
  configureSerialization()
  configureMonitoring()
  configureSwaggerUI()
  configureStatusPage()
  configureRequestValidation()
  configureRouting()
}
