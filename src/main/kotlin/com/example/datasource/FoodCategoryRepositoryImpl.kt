package com.example.datasource

import com.example.models.FoodCategory
import com.example.repository.FoodCategoryRepository
import com.example.tables.DatabaseFactory.dbQuery
import com.example.tables.FoodCategoryTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class FoodCategoryRepositoryImpl : FoodCategoryRepository {

    override suspend fun insertFoodCategories(foodCategory: FoodCategory): FoodCategory? {
        TODO("Not yet implemented")
    }

    override suspend fun getFoodCategories(sellerCategoryId: Int, resultCategoryId: Int): List<FoodCategory?> {
        val foodCategories = dbQuery {
            FoodCategoryTable.select(
                FoodCategoryTable.sellerCategoryId.eq(sellerCategoryId) and
                        FoodCategoryTable.resultCategoryId.eq(resultCategoryId)
            ).map {
                rowToFoodCategory(it)
            }
        }
        return foodCategories
    }

    override suspend fun getFoodCategoriesByTitle(
        sellerCategoryId: Int,
        resultCategoryId: Int,
        foodCategoryTitle: String
    ): List<FoodCategory?> {
        val foodCategories = dbQuery {
            FoodCategoryTable.select(
                FoodCategoryTable.sellerCategoryId.eq(sellerCategoryId) and
                        FoodCategoryTable.resultCategoryId.eq(resultCategoryId) and
                        FoodCategoryTable.title.eq(foodCategoryTitle)
            ).map {
                rowToFoodCategory(it)
            }
        }
        return foodCategories
    }

    override suspend fun getFoodCategoriesByResultCategoryId(
        sellerCategoryId: Int,
        resultCategoryId: Int
    ): List<FoodCategory?> {
        val foodCategories = dbQuery {
            FoodCategoryTable.select(
                FoodCategoryTable.sellerCategoryId.eq(sellerCategoryId) and
                        FoodCategoryTable.resultCategoryId.eq(resultCategoryId)
            ).map {
                rowToFoodCategory(it)
            }
        }
        return foodCategories
    }

    override suspend fun updateFoodCategory(sellerCategoryId: Int, resultCategoryId: Int, foodCategoryId: Int): FoodCategory {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFoodCategory(sellerCategoryId: Int, resultCategoryId: Int, foodCategoryId: Int) {
        dbQuery {
            FoodCategoryTable.deleteWhere {
                FoodCategoryTable.sellerCategoryId.eq(sellerCategoryId) and
                        FoodCategoryTable.resultCategoryId.eq(resultCategoryId) and
                        FoodCategoryTable.id.eq(foodCategoryId)
            }
        }
    }

    override suspend fun deleteFoodCategories() {
        dbQuery {
            FoodCategoryTable.deleteAll()
        }
    }

    private fun rowToFoodCategory(row: ResultRow?): FoodCategory? {
        if (row == null) return null

        return FoodCategory(
            id = row[FoodCategoryTable.id],
            title = row[FoodCategoryTable.title],
            seller_category_id = row[FoodCategoryTable.sellerCategoryId],
            result_category_id = row[FoodCategoryTable.resultCategoryId],
        )
    }
}