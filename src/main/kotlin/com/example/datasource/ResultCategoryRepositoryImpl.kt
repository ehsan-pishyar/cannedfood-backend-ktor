package com.example.datasource

import com.example.models.ResultCategory
import com.example.repository.ResultCategoryRepository
import com.example.tables.DatabaseFactory.dbQuery
import com.example.tables.FoodCategoryTable
import com.example.tables.ResultCategoryTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class ResultCategoryRepositoryImpl : ResultCategoryRepository {

    override suspend fun insertResultCategory(resultCategory: ResultCategory) {
        dbQuery {
            ResultCategoryTable.insert {
                it[title] = resultCategory.title
                it[imagePath] = resultCategory.image_path
                it[sellerCategoryId] = resultCategory.seller_category_id
            }
        }
    }

    override suspend fun getResultCategories(sellerCategoryId: Int): List<ResultCategory?> {
        val resultCategories = dbQuery {
            ResultCategoryTable.select(ResultCategoryTable.sellerCategoryId.eq(sellerCategoryId))
                .orderBy(FoodCategoryTable.fcId to SortOrder.ASC)
                .map {
                rowToResultCategory(it)
            }
        }

        return resultCategories
    }

    override suspend fun getResultCategoriesByTitle(categoryTitle: String?): List<ResultCategory?> {
        val resultCategories = dbQuery {
            ResultCategoryTable.select(
                ResultCategoryTable.title.eq(categoryTitle!!)
            )
                .orderBy(FoodCategoryTable.fcId to SortOrder.ASC)
                .map {
                rowToResultCategory(it)
            }
        }
        return resultCategories
    }

    override suspend fun updateResultCategory(
        resultCategoryId: Int,
        resultCategory: ResultCategory
    ) {
        dbQuery {
            ResultCategoryTable.update({
                ResultCategoryTable.rcId.eq(resultCategoryId)
            }) {
                it[rcId] = resultCategory.rc_id
                it[title] = resultCategory.title
                it[imagePath] = resultCategory.image_path
                it[sellerCategoryId] = resultCategory.seller_category_id
            }
        }
    }

    override suspend fun deleteResultCategory(resultCategoryId: Int) {
        dbQuery {
            ResultCategoryTable.deleteWhere {
                ResultCategoryTable.rcId.eq(resultCategoryId)
            }
        }
    }

    override suspend fun deleteResultCategoriesOfSellerCategory(sellerCategoryId: Int) {
        dbQuery {
            ResultCategoryTable.deleteWhere {
                ResultCategoryTable.sellerCategoryId.eq(sellerCategoryId)
            }
        }
    }

    override suspend fun deleteResultCategories() {
        dbQuery {
            ResultCategoryTable.deleteAll()
        }
    }

    private fun rowToResultCategory(row: ResultRow?): ResultCategory? {
        if (row == null) return null

        return ResultCategory(
            rc_id = row[ResultCategoryTable.rcId],
            title = row[ResultCategoryTable.title],
            image_path = row[ResultCategoryTable.imagePath],
            seller_category_id = row[ResultCategoryTable.sellerCategoryId]
        )
    }
}