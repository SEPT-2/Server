package example.com.domain

import io.swagger.v3.oas.annotations.media.Schema
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object Answers : LongIdTable("answers") {
  val content = text("content")
  val questionId = reference("question_id", Questions, onDelete = ReferenceOption.CASCADE)
  val type = varchar("type", 100)
}

class Answer(id: EntityID<Long>) : LongEntity(id) {
  companion object : LongEntityClass<Answer>(Answers)

  var content by Answers.content
  var question by Question referencedOn Answers.questionId
  var questionId by Answers.questionId
  var type by Answers.type

  @Schema(name = "Answer")
  data class Response(
    val id: Long,
    val content: String,
    val questionId: Long,
    val type: String
  ) {
    constructor(answer: Answer) : this(
      answer.id.value,
      answer.content,
      answer.questionId.value,
      answer.type
    )
  }
}