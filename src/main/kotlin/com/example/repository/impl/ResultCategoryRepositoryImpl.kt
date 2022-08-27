package com.example.repository.impl

import com.example.models.ResultCategory
import com.example.models.responses.ResultsCategoryResponse
import com.example.repository.ResultCategoryRepository
import com.example.tables.*
import com.example.tables.DatabaseFactory.dbQuery
import com.example.utils.ErrorCode
import com.example.utils.ServiceResult
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class ResultCategoryRepositoryImpl : ResultCategoryRepository {

    override suspend fun insertResultCategory(resultCategory: ResultCategory): ServiceResult<ResultCategory> {
        return try {
            dbQuery {
                ResultCategoryTable.insert {
                    it[title] = resultCategory.title
                    it[imagePath] = resultCategory.image_path
                    it[sellerCategoryId] = resultCategory.seller_category_id
                }
                    .resultedValues?.single()?.let {
                        ServiceResult.Success(rowToResultCategory(it)!!)
                    } ?: ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getResultCategories(sellerCategoryId: Int): ServiceResult<List<ResultsCategoryResponse?>> {
        return try {
            dbQuery {
                (ResultCategoryTable innerJoin SellerCategoryTable).select {
                    (ResultCategoryTable.sellerCategoryId eq sellerCategoryId)
                }
                    .orderBy(ResultCategoryTable.id to SortOrder.ASC)
                    .map { rowToResultsCategoryResponse(it)!! }
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

    override suspend fun getResultCategoryById(resultsCategoryId: Int): ServiceResult<ResultsCategoryResponse> {
        return try {
            dbQuery {
                (ResultCategoryTable innerJoin SellerCategoryTable).select {
                    (ResultCategoryTable.id eq resultsCategoryId)
                }
                    .map { rowToResultsCategoryResponse(it)!! }
                    .single()
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

    override suspend fun getResultCategoriesByTitle(resultsCategoryTitle: String?): ServiceResult<List<ResultsCategoryResponse?>> {
        return try {
            dbQuery {
                (ResultCategoryTable innerJoin SellerCategoryTable).select {
                    (ResultCategoryTable.title like "$resultsCategoryTitle%")
                }
                    .orderBy(ResultCategoryTable.id to SortOrder.ASC)
                    .map { rowToResultsCategoryResponse(it)!! }
            }.let {
                ServiceResult.Success(it)
            }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }    }

    override suspend fun updateResultCategory(
        resultsCategoryId: Int,
        resultCategory: ResultCategory
    ): ServiceResult<ResultCategory> {

        return try {
            dbQuery {
                ResultCategoryTable.update({
                    (ResultCategoryTable.id eq resultsCategoryId)
                }) {
                    it[id] = resultsCategoryId
                    it[title] = resultCategory.title
                    it[imagePath] = resultCategory.image_path
                    it[sellerCategoryId] = resultCategory.seller_category_id
                }
                ServiceResult.Success(selectResultsCategoryById(resultsCategoryId))
            }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun deleteResultCategoryById(resultsCategoryId: Int): ServiceResult<List<ResultsCategoryResponse?>> {
        return try {
            dbQuery {
                ResultCategoryTable.deleteWhere {
                    (ResultCategoryTable.id eq resultsCategoryId)
                }
                ServiceResult.Success(selectResultsCategories())
            }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun deleteResultCategoriesOfSellerCategory(resultsCategoryId: Int): ServiceResult<List<ResultsCategoryResponse?>> {
        return try {
            dbQuery {
                ResultCategoryTable.deleteWhere {
                    (ResultCategoryTable.id eq resultsCategoryId)
                }
                ServiceResult.Success(selectResultsCategories())
            }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun deleteResultCategories(): ServiceResult<List<ResultsCategoryResponse?>> {
        return try {
            dbQuery {
                CityTable.deleteAll()
                ServiceResult.Success(selectResultsCategories())
            }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    private fun rowToResultCategory(row: ResultRow?): ResultCategory? {
        if (row == null) return null

        return ResultCategory(
            id = row[ResultCategoryTable.id],
            title = row[ResultCategoryTable.title],
            image_path = row[ResultCategoryTable.imagePath]!!,
            seller_category_id = row[ResultCategoryTable.sellerCategoryId]
        )
    }

    private fun rowToResultsCategoryResponse(row: ResultRow?): ResultsCategoryResponse? {
        if (row == null) return null

        return ResultsCategoryResponse(
            id = row[ResultCategoryTable.id],
            title = row[ResultCategoryTable.title],
            image_path = row[ResultCategoryTable.imagePath]!!,
            seller_category = row[SellerCategoryTable.title]
        )
    }

    private fun selectResultsCategories(): List<ResultsCategoryResponse?> {
        return transaction {
            (ResultCategoryTable innerJoin SellerCategoryTable).selectAll()
                .orderBy(ResultCategoryTable.id to SortOrder.ASC)
                .limit(20)
                .map { rowToResultsCategoryResponse(it) }
        }
    }

    private fun selectResultsCategoryById(resultsCategoryId: Int): ResultCategory {
        return transaction {
            ResultCategoryTable.select {
                (ResultCategoryTable.id eq resultsCategoryId)
            }
                .map {
                    rowToResultCategory(it)!!
                }
                .single()
        }
    }
}