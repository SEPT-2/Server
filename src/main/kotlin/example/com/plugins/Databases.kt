package example.com.plugins

import example.com.domain.Answers
import example.com.domain.Questions
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.sqlite.JDBC

object DatabaseFactory {
  private lateinit var database: Database

  fun init() {
    database = Database.connect(
      "jdbc:sqlite:/Users/daehyeon/Documents/Code/SEPT2/Server/database.sqlite",
      driver = JDBC::class.java.name
    )
    transaction {
      SchemaUtils.createMissingTablesAndColumns(Questions, Answers)
    }
  }

  suspend fun <T> dbQuery(block: suspend () -> T): T = newSuspendedTransaction(Dispatchers.IO, database) { block() }
}