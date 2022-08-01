package com.example.datasource

import com.example.models.Seller
import com.example.repository.SellerRepository
import com.example.tables.DatabaseFactory.dbQuery
import com.example.tables.SellerTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like

class SellerRepositoryImpl : SellerRepository {

    override suspend fun insertSeller(seller: Seller): Seller? {
        TODO("Not yet implemented")
    }

    override suspend fun getSellers(): List<Seller?> {
        val sellers = dbQuery {
            SellerTable.selectAll().map {
                rowToSeller(it)
            }
        }
        return sellers
    }

    override suspend fun getSellersByTitle(sellerTitle: String?): List<Seller?> {
        val sellers = dbQuery {
            SellerTable.select(SellerTable.title.eq(sellerTitle!!)).map {
                rowToSeller(it)
            }
        }
        return sellers
    }

    override suspend fun getSellersByDescription(description: String?): List<Seller?> {
        val sellers = dbQuery {
            SellerTable.select(SellerTable.description.like(description!!)).map {
                rowToSeller(it)
            }
        }
        return sellers
    }

    override suspend fun getSellersByStateId(stateId: Int): List<Seller?> {
        val sellers = dbQuery {
            SellerTable.select(SellerTable.stateId.eq(stateId)).map {
                rowToSeller(it)
            }
        }
        return sellers
    }

    override suspend fun getSellersByStateTitle(stateTitle: String?): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByCityId(stateId: Int, cityId: Int): List<Seller?> {
        val sellers = dbQuery {
            SellerTable.select(SellerTable.stateId.eq(stateId) and SellerTable.cityId.eq(cityId)).map {
                rowToSeller(it)
            }
        }
        return sellers
    }

    override suspend fun getSellersByCityTitle(stateId: Int, cityTitle: String?): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByLocationTitle(stateId: Int, cityId: Int, locationTitle: String?): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByResultsId(resultId: Int): List<Seller?> {
        val sellers = dbQuery {
            SellerTable.select(SellerTable.resultsId.eq(resultId)).map {
                rowToSeller(it)
            }
        }
        return sellers
    }

    override suspend fun getSellersByResultsTitle(resultTitle: String?): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersBySellerCategoryId(sellerCategoryId: Int): List<Seller?> {
        val sellers = dbQuery {
            SellerTable.select(SellerTable.sellerCategoryId.eq(sellerCategoryId)).map {
                rowToSeller(it)
            }
        }
        return sellers
    }

    override suspend fun getSellersBySellerCategoryTitle(sellerCategoryTitle: String?): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByResultCategoryId(sellerCategoryId: Int, resultCategoryId: Int): List<Seller?> {
        val sellers = dbQuery {
            SellerTable.select(
                SellerTable.sellerCategoryId.eq(sellerCategoryId) and
                        SellerTable.resultCategoryId.eq(resultCategoryId)
            ).map {
                rowToSeller(it)
            }
        }
        return sellers
    }

    override suspend fun getSellersByResultCategoryTitle(
        sellerCategoryId: Int,
        resultCategoryTitle: String?
    ): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByFoodCategoryId(
        sellerCategoryId: Int,
        resultCategoryId: Int,
        foodCategoryId: Int
    ): List<Seller?> {

        val sellers = dbQuery {
            SellerTable.select(
                SellerTable.sellerCategoryId.eq(sellerCategoryId) and
                        SellerTable.resultCategoryId.eq(resultCategoryId) and
                        SellerTable.foodCategoryId.eq(foodCategoryId)
            ).map {
                rowToSeller(it)
            }
        }
        return sellers
    }

    override suspend fun getSellersByFoodCategoryTitle(
        sellerCategoryId: Int,
        resultCategoryId: Int,
        foodTypeCategoryTitle: String?
    ): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByOpenStatus(isOpen: Boolean): List<Seller?> {
        val sellers = dbQuery {
            SellerTable.select(SellerTable.isOpen.eq(isOpen)).map {
                rowToSeller(it)
            }
        }
        return sellers
    }

    override suspend fun getSellersByDeliveryDuration(minutes: Int): List<Seller?> {
        val sellers = dbQuery {
            SellerTable.select(SellerTable.deliveryDuration.eq(minutes)).map {
                rowToSeller(it)
            }
        }
        return sellers
    }

    override suspend fun getSellersByDeliveryFee(fee: Int): List<Seller?> {
        val sellers = dbQuery {
            SellerTable.select(SellerTable.deliveryFee.eq(fee)).map {
                rowToSeller(it)
            }
        }
        return sellers
    }

    override suspend fun updateSeller(sellerId: Int, seller: Seller): Seller {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSellerById(sellerId: Int) {
        dbQuery {
            SellerTable.deleteWhere {
                SellerTable.id.eq(sellerId)
            }
        }
    }

    override suspend fun deleteSellersByRating(rating: Double) {
        dbQuery {
            SellerTable.deleteWhere {
                SellerTable.rating.lessEq(rating)
            }
        }
    }

    override suspend fun deleteSellersByStateId(stateId: Int) {
        dbQuery {
            SellerTable.deleteWhere {
                SellerTable.stateId.eq(stateId)
            }
        }
    }

    override suspend fun deleteSellersByStateTitle(stateTitle: String?) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSellersByCityId(cityId: Int) {
        dbQuery {
            SellerTable.deleteWhere {
                SellerTable.cityId.eq(cityId)
            }
        }
    }

    override suspend fun deleteSellersByCityTitle(cityTitle: String?) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSellersByLocationTitle(locationTitle: String?) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSellers() {
        dbQuery {
            SellerTable.deleteAll()
        }
    }

    private fun rowToSeller(row: ResultRow?): Seller? {
        if (row == null) return null

        return Seller(
            id = row[SellerTable.id],
            title = row[SellerTable.title],
            description = row[SellerTable.description],
            logo = row[SellerTable.logo],
            banner = row[SellerTable.banner],
            seller_category_id = row[SellerTable.sellerCategoryId],
            result_category_id = row[SellerTable.resultCategoryId],
            food_category_id = listOf(row[SellerTable.foodCategoryId]),
            state_id = row[SellerTable.stateId],
            city_id = row[SellerTable.cityId],
            location = row[SellerTable.location],
            results_id = listOf(row[SellerTable.resultsId]),
            is_open = row[SellerTable.isOpen],
            rating = row[SellerTable.rating],
            vote_count = row[SellerTable.voteCount],
            delivery_fee = row[SellerTable.deliveryFee],
            delivery_duration = row[SellerTable.deliveryDuration],
        )
    }
}