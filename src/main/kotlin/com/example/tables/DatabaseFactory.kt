package com.example.tables

import com.example.utils.Constants
import com.example.utils.Constants.FLYWAY_DB_PASSWORD
import com.example.utils.Constants.FLYWAY_DB_URL
import com.example.utils.Constants.FLYWAY_DB_USERNAME
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init(){
        Database.connect(hikari())

        val flyway: Flyway = Flyway.configure().dataSource(FLYWAY_DB_URL, FLYWAY_DB_USERNAME, FLYWAY_DB_PASSWORD).load()
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