@file:OptIn(ExperimentalSerializationApi::class)

package example.com.service

import example.com.domain.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy

object OpenAIService {
  private const val URL = "https://api.openai.com/v1/chat/completions"

  private val apiKey = System.getenv("API_KEY")

  private val client = HttpClient(CIO) {
    engine {
      requestTimeout = 60000
    }

    install(ContentNegotiation) {
      json(Json {
        ignoreUnknownKeys = true
        isLenient = true
        namingStrategy = JsonNamingStrategy.SnakeCase
        encodeDefaults = true
      })
    }

  }


  suspend fun fetchQuestion(): OpenAiQuestionResponse {
    val json = Json {
      ignoreUnknownKeys = true
      isLenient = true
      namingStrategy = JsonNamingStrategy.SnakeCase
      encodeDefaults = true
    }

    val response = client.post(URL) {
      headers {
        bearerAuth(apiKey)
        contentType(ContentType.Application.Json)
      }
      setBody(OpenAiRequest(messages = listOf(Message(PROMPT, Role.system))))
    }

    val body = response.body<OpenAiResponse>()
    val content = body.choices.first().message.content
    val questions = json.decodeFromString<OpenAiQuestionResponse>(content)

    return questions
  }


  private const val PROMPT = """

    1. Question and answer generation service to analyze political tendencies by arousing interest to encourage political participation in the 2030 generation
    2. 정치 성향은 보수, 진보, 중도, 사회, 민족 5가지로 분류할 거야.
    3. Using the format of <참고> below, generate one question in the form of multiple choice and five concise answers representing five political orientations in the form of json. Description and annotation should not be added.

    <참고>
    {
    "questions": [
        {
            "question": "2030세대가 정치에 참여할 때 가장 중요하게 생각하는 가치는 무엇인가요?",
            "field": "정치 참여",
            "answers": [
                {
                    "answer": "평등과 사회 정의를 추구하는 가치입니다.",
                    "type": "진보"
                },
                {
                    "answer": "자유와 경제 발전을 중시하는 가치입니다.",
                    "type": "보수"
                },
                {
                    "answer": "희망과 변화를 중요시하는 가치입니다.",
                    "type": "중도"
                },
                {
                    "answer": "우리 민족의 정체성과 문화를 지키는 가치입니다.",
                    "type": "민족"
                },
                {
                    "answer": "사회 안전과 복지를 중요시하는 가치입니다.",
                    "type": "사회"
                }
            ]
        },
       
    4. Direct questions and answers should not be asked so that the respondent does not feel burdened and can give light, interesting answers.
    5. Generate questions with political neutrality
    6. The questions you create must not have overlapping content
    7. Try to ask questions from as many different fields as possible
    8. Include information about which field of politics
    9. Include information about the political leanings
    10. Avoid mentioning targeting the 2030 generation specifically
    11. 질문 분야는 정치 참여, 경제 정책, 사회 복지, 환경 정책, 교육 정책, 민족 정체성, 건강 정책, 법과 질서, 국제 관계, 인권과 자유, 기술 발전, 도시 및 지역 개발 12개
    12. Create 7 questions
    13. Need to answer in Korean"""
}