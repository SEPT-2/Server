package example.com.domain

data class OpenAiRequest(
  val model: String = "gpt-3.5-turbo-0125",
  val maxTokens: Int = 4096,
  val messages: List<Message>
)

data class Message(
  val content: String,
  val role: Role,
  val name: String?
)

enum class Role {
  system, user, assistant
}

data class OpenAiResponse(
  val id: String,
  val created: Int,
  val systemFingerPrint: String,
  val `object`: String,
  val choices: List<Choice>,
  val usage: Usage
)

data class Choice(
  val finishReason: String,
  val index: Int,
  val message: Message,
)

data class Usage(
  val completionTokens: Int,
  val promptTokens: Int,
  val totalTokens: Int
)