package example.com.domain

import io.swagger.v3.oas.annotations.media.Schema
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object Questions : LongIdTable("questions") {
  val content = text("content")
  val field = varchar("field", 100)
  val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }
}

class Question(id: EntityID<Long>) : LongEntity(id) {
  companion object : LongEntityClass<Question>(Questions)

  var content by Questions.content
  var field by Questions.field
  var createdAt by Questions.createdAt
  val answers by Answer referrersOn Answers.questionId


  @Schema(name = "Question")
  data class Response(
    val id: Long,
    val content: String,
    val field: String,
    val createdAt: LocalDateTime,
    val answers: List<Answer.Response>
  ) {
    constructor(question: Question) : this(
      question.id.value,
      question.content,
      question.field,
      question.createdAt,
      question.answers.map { Answer.Response(it) }
    )
  }
}

