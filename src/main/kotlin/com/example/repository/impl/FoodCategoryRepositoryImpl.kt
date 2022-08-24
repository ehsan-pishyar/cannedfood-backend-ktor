package com.example.repository.impl

import com.example.models.FoodCategory
import com.example.models.responses.FoodCategoryResponse
import com.example.repository.FoodCategoryRepository
import com.example.tables.DatabaseFactory.dbQuery
import com.example.tables.FoodCategoryTable
import com.example.tables.ResultCategoryTable
import com.example.tables.SellerCategoryTable
import com.example.utils.ErrorCode
import com.example.utils.ServiceResult
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class FoodCategoryRepositoryImpl : FoodCategoryRepository {

    override suspend fun insertFoodCategory(foodCategory: FoodCategory): ServiceResult<FoodCategory> {
        return try {
            dbQuery {
                FoodCategoryTable.insert {
                    it[title] = foodCategory.title
                    it[imagePath] = foodCategory.image_path
                    it[resultCategoryId] = foodCategory.result_category_id
                }
                    .resultedValues?.single()?.let {
                        ServiceResult.Success(rowToFoodCategory(it)!!)
                    } ?: ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getFoodCategories(resultCategoryId: Int): ServiceResult<List<FoodCategoryResponse?>> {
        return try {
            dbQuery {
                (FoodCategoryTable innerJoin ResultCategoryTable).select {
                    (FoodCategoryTable.resultCategoryId eq resultCategoryId)
                }
                    .orderBy(FoodCategoryTable.id to SortOrder.ASC)
                    .map { rowToFoodCategoryResponse(it)!! }
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

    override suspend fun getFoodCategoryById(foodCategoryId: Int): ServiceResult<FoodCategoryResponse> {
        return try {
            dbQuery {
                (FoodCategoryTable innerJoin SellerCategoryTable).select {
                    (FoodCategoryTable.id eq foodCategoryId)
                }
                    .orderBy(FoodCategoryTable.id to SortOrder.ASC)
                    .map { rowToFoodCategoryResponse(it)!! }
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

    override suspend fun getFoodCategoriesByTitle(foodCategoryTitle: String?): ServiceResult<List<FoodCategoryResponse?>> {
        return try {
            dbQuery {
                (FoodCategoryTable innerJoin SellerCategoryTable).select {
                    (FoodCategoryTable.title eq "$foodCategoryTitle%")
                }
                    .orderBy(FoodCategoryTable.id to SortOrder.ASC)
                    .map { rowToFoodCategoryResponse(it)!! }
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

    override suspend fun updateFoodCategory(
        foodCategoryId: Int,
        foodCategory: FoodCategory
    ): ServiceResult<FoodCategory> {

        return try {
            dbQuery {
                FoodCategoryTable.update({
                    (FoodCategoryTable.id eq foodCategoryId)
                }) {
                    it[id] = foodCategoryId
                    it[title] = foodCategory.title
                    it[imagePath] = foodCategory.image_path
                    it[resultCategoryId] = foodCategory.result_category_id
                }
                ServiceResult.Success(selectFoodCategoryById(foodCategoryId))
            }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun deleteFoodCategoryById(foodCategoryId: Int): ServiceResult<List<FoodCategoryResponse?>> {
        return try {
            dbQuery {
                FoodCategoryTable.deleteWhere {
                    (FoodCategoryTable.id eq foodCategoryId)
                }
                // برای دریافت لیست دسته بندی غذا ها (جهت جلوگیری از تکرار این قسمت کد در همه توابع)
                ServiceResult.Success(selectFoodCategories())
            }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun deleteFoodCategoriesOfResultCategory(resultCategoryId: Int): ServiceResult<List<FoodCategoryResponse?>> {
        return try {
            dbQuery {
                FoodCategoryTable.deleteWhere {
                    (FoodCategoryTable.resultCategoryId eq resultCategoryId)
                }
                ServiceResult.Success(selectFoodCategories())
            }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun deleteFoodCategories(): ServiceResult<List<FoodCategoryResponse?>> {
        return try {
            dbQuery {
                ResultCategoryTable.deleteAll()
                ServiceResult.Success(selectFoodCategories())
            }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    private fun rowToFoodCategory(row: ResultRow?): FoodCategory? {
        if (row == null) return null

        return FoodCategory(
            id = row[FoodCategoryTable.id],
            title = row[FoodCategoryTable.title],
            image_path = row[FoodCategoryTable.imagePath]!!,
            result_category_id = row[FoodCategoryTable.resultCategoryId],
        )
    }

    private fun rowToFoodCategoryResponse(row: ResultRow?): FoodCategoryResponse? {
        if (row == null) return null

        return FoodCategoryResponse(
            id = row[FoodCategoryTable.id],
            title = row[FoodCategoryTable.title],
            image_path = row[FoodCategoryTable.imagePath]!!,
            results_category = row[ResultCategoryTable.title],
        )
    }

    private fun selectFoodCategories(): List<FoodCategoryResponse> {
        return transaction {
            (FoodCategoryTable innerJoin ResultCategoryTable).selectAll()
                .orderBy(FoodCategoryTable.id to SortOrder.ASC)
                .map { rowToFoodCategoryResponse(it)!! }
        }
    }

    private fun selectFoodCategoryById(foodCategoryId: Int): FoodCategory {
        return transaction {
            FoodCategoryTable.select {
                (FoodCategoryTable.id eq foodCategoryId)
            }
                .map {
                    rowToFoodCategory(it)!!
                }
                .single()
        }
    }
}