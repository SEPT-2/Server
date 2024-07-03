package example.com.plugins

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import example.com.domain.Answers
import example.com.domain.Questions
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.postgresql.Driver

object DatabaseFactory {
  private lateinit var database: Database

  fun init() {
    database = Database.connect(hirakiCP())
    transaction {
      SchemaUtils.createMissingTablesAndColumns(Questions, Answers)
    }
  }

  private fun hirakiCP() = HikariConfig().apply {
    username = System.getenv("DB_USER")
    password = System.getenv("DB_PASSWORD")
    jdbcUrl = System.getenv("DB_URL")
    driverClassName = Driver::class.java.name
    maximumPoolSize = 10
  }.let { HikariDataSource(it) }

  suspend fun <T> dbQuery(block: suspend () -> T): T = newSuspendedTransaction(Dispatchers.IO, database) { block() }
}