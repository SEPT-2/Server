package example.com.repository

import example.com.domain.OpenAiQuestion
import example.com.domain.Question
import example.com.domain.Questions
import org.jetbrains.exposed.sql.SortOrder

class QuestionRepository {

  fun findAll() =
    Question.all().orderBy(Questions.createdAt to SortOrder.DESC).toList()

  fun addQuestions(questions: List<OpenAiQuestion>): List<Question> {
    return questions.map {
      Question.new {
        content = it.question
        field = it.field
      }
    }
  }
}