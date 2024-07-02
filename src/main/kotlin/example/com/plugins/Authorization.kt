import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.util.*

class ApiKeyHeader(configure: Configuration) {
  private val name = configure.headerName
  private val value = configure.headerValue

  class Configuration {
    val headerName = "SERVER-KEY"
    val headerValue = System.getenv("SERVER_KEY")
  }

  companion object Plugin : BaseApplicationPlugin<ApplicationCallPipeline, Configuration, ApiKeyHeader> {
    override val key: AttributeKey<ApiKeyHeader> = AttributeKey("ApiKeyHeader")


    override fun install(pipeline: ApplicationCallPipeline, configure: Configuration.() -> Unit): ApiKeyHeader {
      val configuration = Configuration().apply(configure)
      val plugin = ApiKeyHeader(configuration)
      pipeline.intercept(ApplicationCallPipeline.Plugins) {
        if (call.request.headers[plugin.name] != plugin.value) {
          call.respond(HttpStatusCode.Unauthorized, "Unauthorized")
          finish()
        }
      }
      return plugin
    }
  }
}

fun Application.configureApiKeyHeader() {
  install(ApiKeyHeader)
}