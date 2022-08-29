package com.example.tables

import com.example.utils.Constants
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    private val dbUrl = "jdbc:postgresql:cannedfood_db"
    private val dbUsername = "postgres"
    private val dbPassword = "55989525"

    fun init(){
        Database.connect(hikari())

        val flyway: Flyway = Flyway.configure().dataSource(dbUrl, dbUsername, dbPassword).load()
        flyway.baseline()
        flyway.migrate()

        transaction {
//            SchemaUtils.drop(StateTable, CityTable, LocationTable, SellerCategoryTable, ResultCategoryTable,
//                FoodCategoryTable, SellerRatingTable, ResultRatingTable, SellerCommentTable, ResultCommentTable,
//                CustomerTable, UserTable, ResultsTable, SellerTable, SellerOpenStatusTable)

            SchemaUtils.create(StateTable, CityTable, LocationTable, SellerCategoryTable, ResultCategoryTable,
            FoodCategoryTable, SellerRatingTable, ResultRatingTable, SellerCommentTable, ResultCommentTable,
                SellerTable, UserTable, SellerOpenStatusTable)
        }
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig().apply {
            driverClassName = System.getenv(Constants.JDBC_DRIVER)
            jdbcUrl = System.getenv(Constants.DB_URL)
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = Constants.TRANSACTION_READ
            validate()
        }

        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(IO) {
            transaction { block() }
        }
}