package example.com.plugins

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.serialization.jackson.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

fun Application.configureSerialization() {
  install(ContentNegotiation) {
    json()
    jackson {
      enable(SerializationFeature.INDENT_OUTPUT)
      propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE
    }
  }
}
