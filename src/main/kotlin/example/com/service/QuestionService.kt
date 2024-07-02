package example.com.service

import example.com.domain.Question
import example.com.plugins.DatabaseFactory.dbQuery
import example.com.repository.AnswerRepository
import example.com.repository.QuestionRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class QuestionService : KoinComponent {

  private val questionRepository by inject<QuestionRepository>()
  private val answerRepository by inject<AnswerRepository>()

  suspend fun getAllQuestion() = dbQuery {
    val questions = questionRepository.findAll()

    questions.map { Question.Response(it) }.toList()
  }

  suspend fun fetchQuestionFromOpenAI() {
    val questions = OpenAIService.fetchQuestion()
    val questionsAdded = dbQuery { questionRepository.addQuestions(questions.questions) }

    questionsAdded.forEach { q ->
      val answers = questions.questions.filter { it.question == q.content }.flatMap { it.answers }

      dbQuery { answerRepository.addAnswers(q, answers) }
    }

  }
}