package com.example.datasource

import com.example.models.FoodCategory
import com.example.repository.FoodCategoryRepository
import com.example.tables.DatabaseFactory.dbQuery
import com.example.tables.FoodCategoryTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class FoodCategoryRepositoryImpl : FoodCategoryRepository {

    override suspend fun insertFoodCategory(
        foodCategory: FoodCategory
    ) {
        dbQuery {
            FoodCategoryTable.insert {
                it[title] = foodCategory.title
                it[imagePath] = foodCategory.image_path
                it[resultCategoryId] = foodCategory.result_category_id
            }
        }
    }

    override suspend fun getFoodCategories(resultCategoryId: Int): List<FoodCategory?> {
        val foodCategories = dbQuery {
            FoodCategoryTable.select(
                FoodCategoryTable.resultCategoryId.eq(resultCategoryId))
                .orderBy(FoodCategoryTable.fcId to SortOrder.ASC)
                .map {
                    rowToFoodCategory(it)
            }
        }
        return foodCategories
    }

    override suspend fun getFoodCategoriesByTitle(
        foodCategoryTitle: String?
    ): List<FoodCategory?> {
        val foodCategories = dbQuery {
            FoodCategoryTable.select(
                FoodCategoryTable.title.eq(foodCategoryTitle!!))
                .orderBy(FoodCategoryTable.fcId to SortOrder.ASC)
                .map {
                    rowToFoodCategory(it)
            }
        }
        return foodCategories
    }

    override suspend fun updateFoodCategory(
        foodCategoryId: Int,
        foodCategory: FoodCategory
    ) {
        dbQuery {
            FoodCategoryTable.update({
                FoodCategoryTable.fcId.eq(foodCategoryId)
            }) {
                it[fcId] = foodCategory.fc_id
                it[title] = foodCategory.title
                it[imagePath] = foodCategory.image_path
                it[resultCategoryId] = foodCategory.result_category_id
            }
        }
    }

    override suspend fun deleteFoodCategory(foodCategoryId: Int) {
        dbQuery {
            FoodCategoryTable.deleteWhere {
                FoodCategoryTable.fcId.eq(foodCategoryId)
            }
        }
    }

    override suspend fun deleteFoodCategoriesOfResultCategory(resultCategoryId: Int) {
        dbQuery {
            FoodCategoryTable.deleteWhere {
                FoodCategoryTable.resultCategoryId.eq(resultCategoryId)
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
            fc_id = row[FoodCategoryTable.fcId],
            title = row[FoodCategoryTable.title],
            image_path = row[FoodCategoryTable.imagePath],
            result_category_id = row[FoodCategoryTable.resultCategoryId],
        )
    }
}