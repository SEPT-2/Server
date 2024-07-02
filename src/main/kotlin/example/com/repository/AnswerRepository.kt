package example.com.repository

import example.com.domain.Answer
import example.com.domain.OpenAiAnswer
import example.com.domain.Question

class AnswerRepository {

  fun addAnswers(question: Question, answers: List<OpenAiAnswer>) {
    answers.forEach {
      Answer.new {
        content = it.answer
        this.question = question
        type = it.type
      }
    }
  }
}