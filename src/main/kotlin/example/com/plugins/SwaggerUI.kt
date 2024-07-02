package example.com.plugins

import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.ktor.server.application.*

fun Application.configureSwaggerUI() {

  install(SwaggerUI) {
    swagger {
      swaggerUrl = "swagger-ui"
      forwardRoot = true
    }
    info {
      title = "SEPT&2 API"
      version = "1.0.0"
      description = "초간단 정치 성향 테스트 API"
    }
    server {
      url = "http://localhost:8080"
      description = "Local Server"
    }
    
  }
}