package com.example.datasource

import com.example.models.ResultCategory
import com.example.repository.ResultCategoryRepository
import com.example.tables.DatabaseFactory.dbQuery
import com.example.tables.ResultCategoryTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class ResultCategoryRepositoryImpl : ResultCategoryRepository {

    override suspend fun insertResultCategory(sellerCategoryId: Int, resultCategory: ResultCategory): ResultCategory? {
        TODO("Not yet implemented")
    }

    override suspend fun getResultCategories(sellerCategoryId: Int): List<ResultCategory?> {
        val resultCategories = dbQuery {
            ResultCategoryTable.select(ResultCategoryTable.sellerCategoryId.eq(sellerCategoryId)).map {
                rowToResultCategory(it)
            }
        }

        return resultCategories
    }

    override suspend fun getResultCategoriesByTitle(sellerCategoryId: Int, categoryTitle: String?): List<ResultCategory?> {
        val resultCategories = dbQuery {
            ResultCategoryTable.select(
                ResultCategoryTable.sellerCategoryId.eq(sellerCategoryId) and
                ResultCategoryTable.title.eq(categoryTitle!!)
            ).map {
                rowToResultCategory(it)
            }
        }
        return resultCategories
    }

    override suspend fun getResultCategoriesBySellerCategoryId(sellerCategoryId: Int): List<ResultCategory?> {
        val resultCategories = dbQuery {
            ResultCategoryTable.select(ResultCategoryTable.sellerCategoryId.eq(sellerCategoryId)).map {
                rowToResultCategory(it)
            }
        }

        return resultCategories
    }

    override suspend fun updateResultCategory(
        sellerCategoryId: Int,
        resultCategoryId: Int,
        resultCategory: ResultCategory
    ): ResultCategory {
        TODO("Not yet implemented")
    }

    override suspend fun deleteResultCategory(sellerCategoryId: Int, resultCategoryId: Int) {
        dbQuery {
            ResultCategoryTable.deleteWhere {
                ResultCategoryTable.sellerCategoryId.eq(sellerCategoryId) and ResultCategoryTable.id.eq(resultCategoryId)
            }
        }
    }

    override suspend fun deleteCategories() {
        dbQuery {
            ResultCategoryTable.deleteAll()
        }
    }

    private fun rowToResultCategory(row: ResultRow?): ResultCategory? {
        if (row == null) return null

        return ResultCategory(
            id = row[ResultCategoryTable.id],
            title = row[ResultCategoryTable.title],
            seller_category_id = row[ResultCategoryTable.sellerCategoryId]
        )
    }
}