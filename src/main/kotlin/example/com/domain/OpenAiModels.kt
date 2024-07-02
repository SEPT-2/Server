package example.com.domain

import kotlinx.serialization.Serializable


@Serializable
data class OpenAiRequest(
  val model: String = "gpt-3.5-turbo-0125",
  val maxTokens: Int = 4096,
  val messages: List<Message>
)

@Serializable
data class Message(
  val content: String,
  val role: Role,
  val name: String? = null
)

enum class Role {
  system, user, assistant
}

@Serializable
data class OpenAiResponse(
  val id: String,
  val `object`: String,
  val created: Int,
  val model: String,
  val systemFingerPrint: String? = null,
  val choices: List<Choice>,
  val usage: Usage
)

@Serializable
data class Choice(
  val index: Int,
  val finishReason: String,
  val message: Message,
)

@Serializable
data class Usage(
  val completionTokens: Int,
  val promptTokens: Int,
  val totalTokens: Int
)

@Serializable
data class OpenAiQuestionResponse(
  val questions: List<OpenAiQuestion>
)

@Serializable
data class OpenAiQuestion(
  val question: String,
  val field: String,
  val answers: List<OpenAiAnswer>
)

@Serializable
data class OpenAiAnswer(
  val answer: String,
  val type: String
)