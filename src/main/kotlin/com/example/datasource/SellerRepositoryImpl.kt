package com.example.datasource

import com.example.models.Seller
import com.example.repository.SellerRepository
import com.example.tables.DatabaseFactory.dbQuery
import com.example.tables.CustomerTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like

class SellerRepositoryImpl : SellerRepository {

    override suspend fun insertSeller(seller: Seller) {
        dbQuery {
            CustomerTable.insert {
                it[title] = seller.title
                it[description] = seller.description!!
                it[logo] = seller.logo!!
                it[banner] = seller.banner!!
                it[sellerCategoryId] = seller.seller_category_id
                it[resultCategoryId] = seller.result_category_id
                it[stateId] = seller.state_id
                it[cityId] = seller.city_id
                it[location] = seller.location
                it[deliveryFee] = seller.delivery_fee
                it[deliveryDuration] = seller.delivery_duration
                it[userId] = seller.user_id
            }
        }
    }

    override suspend fun getSellers(): List<Seller?> {
        val sellers = dbQuery {
            CustomerTable.selectAll().map {
                rowToSeller(it)
            }
        }
        return sellers
    }

    override suspend fun getSellersByTitle(sellerTitle: String?): List<Seller?> {
        val sellers = dbQuery {
            CustomerTable.select(CustomerTable.title.eq(sellerTitle!!)).map {
                rowToSeller(it)
            }
        }
        return sellers
    }

    override suspend fun getSellersByDescription(description: String?): List<Seller?> {
        val sellers = dbQuery {
            CustomerTable.select(CustomerTable.description.like(description!!)).map {
                rowToSeller(it)
            }
        }
        return sellers
    }

    override suspend fun getSellersByStateId(stateId: Int): List<Seller?> {
        val sellers = dbQuery {
            CustomerTable.select(CustomerTable.stateId.eq(stateId)).map {
                rowToSeller(it)
            }
        }
        return sellers
    }

    override suspend fun getSellersByCityId(cityId: Int): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByLocationTitle(locationTitle: String?): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByResultsId(resultId: Int): List<Seller?> {
        val sellers = dbQuery {
            CustomerTable.select(CustomerTable.resultsId.eq(resultId)).map {
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
            CustomerTable.select(CustomerTable.sellerCategoryId.eq(sellerCategoryId)).map {
                rowToSeller(it)
            }
        }
        return sellers
    }

    override suspend fun getSellersByResultCategoryId(resultCategoryId: Int): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByFoodCategoryId(foodCategoryId: Int): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByOpenStatus(isOpen: Boolean): List<Seller?> {
        val sellers = dbQuery {
            CustomerTable.select(CustomerTable.isOpen.eq(isOpen)).map {
                rowToSeller(it)
            }
        }
        return sellers
    }

    override suspend fun getSellersByDeliveryDuration(minutes: Int): List<Seller?> {
        val sellers = dbQuery {
            CustomerTable.select(CustomerTable.deliveryDuration.eq(minutes)).map {
                rowToSeller(it)
            }
        }
        return sellers
    }

    override suspend fun getSellersByDeliveryFee(fee: Int): List<Seller?> {
        val sellers = dbQuery {
            CustomerTable.select(CustomerTable.deliveryFee.eq(fee)).map {
                rowToSeller(it)
            }
        }
        return sellers
    }

    override suspend fun updateSeller(sellerId: Int, seller: Seller) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSellerById(sellerId: Int) {
        dbQuery {
            CustomerTable.deleteWhere {
                CustomerTable.id.eq(sellerId)
            }
        }
    }

    override suspend fun deleteSellersByRating(rating: Double) {
        dbQuery {
            CustomerTable.deleteWhere {
                CustomerTable.rating.lessEq(rating)
            }
        }
    }

    override suspend fun deleteSellersByStateId(stateId: Int) {
        dbQuery {
            CustomerTable.deleteWhere {
                CustomerTable.stateId.eq(stateId)
            }
        }
    }

    override suspend fun deleteSellersByCityId(cityId: Int) {
        dbQuery {
            CustomerTable.deleteWhere {
                CustomerTable.cityId.eq(cityId)
            }
        }
    }

    override suspend fun deleteSellerByLocation(location: String?) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSellers() {
        dbQuery {
            CustomerTable.deleteAll()
        }
    }

    private fun rowToSeller(row: ResultRow?): Seller? {
        if (row == null) return null

        return Seller(
            seller_id = row[CustomerTable.id],
            title = row[CustomerTable.title],
            description = row[CustomerTable.description],
            logo = row[CustomerTable.logo],
            banner = row[CustomerTable.banner],
            seller_category_id = row[CustomerTable.sellerCategoryId],
            result_category_id = row[CustomerTable.resultCategoryId],
            state_id = row[CustomerTable.stateId],
            city_id = row[CustomerTable.cityId],
            location = row[CustomerTable.location],
            delivery_fee = row[CustomerTable.deliveryFee],
            delivery_duration = row[CustomerTable.deliveryDuration],
            user_id = row[CustomerTable.userId]
        )
    }
}