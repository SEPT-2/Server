package example.com.service

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SNAKE_CASE
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.jackson.*

object OpenAIService {
  private const val url = "https://api.openai.com/v1/chat/completions"

  private val apiKey = System.getenv("API_KEY")

  private val client = HttpClient(CIO) {
    engine {
      requestTimeout = 60000
    }

    install(ContentNegotiation) {
      jackson {
        enable(SerializationFeature.INDENT_OUTPUT)
        propertyNamingStrategy = SNAKE_CASE
      }
    }

  }


  suspend fun fetchQuestion() {
    client.get(url) {
      headers {
        bearerAuth(apiKey)
      }
    }
  }
}