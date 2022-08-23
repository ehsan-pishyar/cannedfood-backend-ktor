package com.example.datasource

import com.example.models.SellerCategory
import com.example.repository.SellerCategoryRepository
import com.example.tables.CityTable
import com.example.tables.DatabaseFactory.dbQuery
import com.example.tables.FoodCategoryTable
import com.example.tables.SellerCategoryTable
import com.example.tables.StateTable
import com.example.utils.ErrorCode
import com.example.utils.ServiceResult
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class SellerCategoryRepositoryImpl : SellerCategoryRepository {

    override suspend fun getSellersCategories(): ServiceResult<List<SellerCategory?>> {
        return try {
            dbQuery {
                SellerCategoryTable.selectAll()
                    .orderBy(SellerCategoryTable.id to SortOrder.ASC)
                    .map {
                        rowToSellerCategory(it)
                    }
            }.let {
                ServiceResult.Success(it)
            }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getSellersCategoryById(id: Int): ServiceResult<SellerCategory> {
        return try {
            dbQuery {
                SellerCategoryTable.select {
                    (SellerCategoryTable.id eq id)
                }
                    .map {
                        rowToSellerCategory(it)
                    }
                    .single()
            }.let {
                ServiceResult.Success(it!!)
            }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getSellerCategoriesByTitle(title: String?): ServiceResult<List<SellerCategory?>> {
        return try {
            dbQuery {
                SellerCategoryTable.select {
                    (SellerCategoryTable.title like "$title%")
                }
                    .map {
                        rowToSellerCategory(it)
                    }
            }.let {
                ServiceResult.Success(it)
            }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun deleteSellerCategory(id: Int): ServiceResult<List<SellerCategory?>> {
        return try {
            dbQuery {
                SellerCategoryTable.deleteWhere {
                    (SellerCategoryTable.id eq id)
                }
                ServiceResult.Success(selectSellerCategories())
            }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun deleteSellerCategories(): ServiceResult<List<SellerCategory?>> {
        return try {
            dbQuery {
                SellerCategoryTable.deleteAll()
                ServiceResult.Success(selectSellerCategories())
            }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    private fun rowToSellerCategory(row: ResultRow?): SellerCategory? {
        if (row == null) return null

        return SellerCategory(
            id = row[SellerCategoryTable.id],
            title = row[SellerCategoryTable.title],
            image_path = row[SellerCategoryTable.imagePath]!!
        )
    }

    private fun selectSellerCategories(): List<SellerCategory?> {
        return transaction {
            (SellerCategoryTable).selectAll()
                .orderBy(SellerCategoryTable.id to SortOrder.ASC)
                .map { rowToSellerCategory(it) }
        }
    }
}