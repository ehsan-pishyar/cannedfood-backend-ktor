package com.example.datasource

import com.example.models.SellerCategory
import com.example.repository.SellerCategoryRepository
import com.example.tables.DatabaseFactory.dbQuery
import com.example.tables.SellerCategoryTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class SellerCategoryRepositoryImpl : SellerCategoryRepository {

    override suspend fun getSellersCategories(): List<SellerCategory?> {
        val sellerCategories = dbQuery {
            SellerCategoryTable.selectAll().map {
                rowToSellerCategory(it)
            }
        }

        return sellerCategories
    }

    override suspend fun getSellerCategoryByTitle(sellerCategoryTitle: String?): List<SellerCategory?> {
        val sellerCategory = dbQuery {
            SellerCategoryTable.select(SellerCategoryTable.title.eq(sellerCategoryTitle!!)).map {
                rowToSellerCategory(it)
            }
        }

        return sellerCategory
    }

    override suspend fun deleteSellerCategoryById(sellerCategoryId: Int) {
        dbQuery {
            SellerCategoryTable.deleteWhere {
                SellerCategoryTable.id.eq(sellerCategoryId)
            }
        }
    }

    override suspend fun deleteSellerCategories() {
        dbQuery {
            SellerCategoryTable.deleteAll()
        }
    }

    private fun rowToSellerCategory(row: ResultRow?): SellerCategory? {
        if (row == null) return null

        return SellerCategory(
            id = row[SellerCategoryTable.id],
            title = row[SellerCategoryTable.title]
        )
    }
}