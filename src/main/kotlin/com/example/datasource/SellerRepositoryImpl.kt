package com.example.datasource

import com.example.models.Seller
import com.example.repository.SellerRepository
import com.example.tables.DatabaseFactory.dbQuery
import com.example.tables.SellerTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like

class SellerRepositoryImpl : SellerRepository {

    override suspend fun insertSeller(seller: Seller){
        dbQuery {
            SellerTable.insert {
                it[id] = seller.id
                it[title] = seller.title
                it[description] = seller.description!!
                it[logo] = seller.logo!!
                it[banner] = seller.banner!!
                it[sellerCategoryId] = seller.seller_category_id
                it[resultCategoryId] = seller.result_category_id
                it[foodCategoryId] = seller.food_category_id!!
                it[stateId] = seller.state_id
                it[cityId] = seller.city_id
                it[location] = seller.location
                it[resultsId] = seller.results_id
                it[isOpen] = seller.is_open!!
                it[rating] = seller.rating!!
                it[voteCount] = seller.vote_count!!
                it[deliveryFee] = seller.delivery_fee
                it[deliveryDuration] = seller.delivery_duration
                it[userId] = seller.user_id
            }
        }
    }

    override suspend fun getSellers(): List<Seller?> {
        val sellers = dbQuery {
            SellerTable.selectAll()
                .orderBy(SellerTable.id to SortOrder.ASC)
                .map {
                rowToSeller(it)
            }
        }
        return sellers
    }

    override suspend fun getSellersByTitle(sellerTitle: String?): List<Seller?> {
        val sellers = dbQuery {
            SellerTable.select(SellerTable.title.like(sellerTitle!!))
                .orderBy(SellerTable.id to SortOrder.ASC)
                .map {
                rowToSeller(it)
            }
        }
        return sellers
    }

    override suspend fun getSellersByDescription(description: String?): List<Seller?> {
        val sellers = dbQuery {
            SellerTable.select(SellerTable.description.like(description!!))
                .orderBy(SellerTable.id to SortOrder.ASC)
                .map {
                rowToSeller(it)
            }
        }
        return sellers
    }

    override suspend fun getSellersByStateId(stateId: Int): List<Seller?> {
        val sellers = dbQuery {
            SellerTable.select(SellerTable.stateId.eq(stateId))
                .orderBy(SellerTable.id to SortOrder.ASC)
                .map {
                    rowToSeller(it)
            }
        }
        return sellers
    }

    override suspend fun getSellersByCityId(cityId: Int): List<Seller?> {
        val sellers = dbQuery {
            SellerTable.select(SellerTable.cityId.eq(cityId))
                .orderBy(SellerTable.id to SortOrder.ASC)
                .map {
                    rowToSeller(it)
            }
        }
        return sellers
    }

    override suspend fun getSellersByLocationTitle(locationTitle: String?): List<Seller?> {
        val sellers = dbQuery {
            SellerTable.select(SellerTable.location.like(locationTitle!!))
                .orderBy(SellerTable.id to SortOrder.ASC)
                .map {
                    rowToSeller(it)
                }
        }
        return sellers
    }

    override suspend fun getSellersByResultsId(resultId: Int): List<Seller?> {
        val sellers = dbQuery {
            SellerTable.select(SellerTable.resultsId.eq(resultId))
                .orderBy(SellerTable.id to SortOrder.ASC)
                .map {
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
            SellerTable.select(SellerTable.sellerCategoryId.eq(sellerCategoryId))
                .orderBy(SellerTable.id to SortOrder.ASC)
                .map {
                    rowToSeller(it)
            }
        }
        return sellers
    }

    override suspend fun getSellersByResultCategoryId(resultCategoryId: Int): List<Seller?> {
        val sellers = dbQuery {
            SellerTable.select(
                SellerTable.resultCategoryId.eq(resultCategoryId)
            )
                .orderBy(SellerTable.id to SortOrder.ASC)
                .map {
                    rowToSeller(it)
            }
        }
        return sellers
    }

    override suspend fun getSellersByFoodCategoryId(
        foodCategoryId: Int
    ): List<Seller?> {

        val sellers = dbQuery {
            SellerTable.select(
                SellerTable.foodCategoryId.eq(foodCategoryId)
            )
                .orderBy(SellerTable.id to SortOrder.ASC)
                .map {
                rowToSeller(it)
            }
        }
        return sellers
    }

    override suspend fun getSellersByOpenStatus(isOpen: Boolean): List<Seller?> {
        val sellers = dbQuery {
            SellerTable.select(SellerTable.isOpen.eq(isOpen))
                .orderBy(SellerTable.id to SortOrder.ASC)
                .map {
                    rowToSeller(it)
            }
        }
        return sellers
    }

    override suspend fun getSellersByDeliveryDuration(minutes: Int): List<Seller?> {
        val sellers = dbQuery {
            SellerTable.select(SellerTable.deliveryDuration.eq(minutes))
                .orderBy(SellerTable.id to SortOrder.ASC)
                .map {
                rowToSeller(it)
            }
        }
        return sellers
    }

    override suspend fun getSellersByDeliveryFee(fee: Int): List<Seller?> {
        val sellers = dbQuery {
            SellerTable.select(SellerTable.deliveryFee.eq(fee))
                .orderBy(SellerTable.id to SortOrder.ASC)
                .map {
                rowToSeller(it)
            }
        }
        return sellers
    }

    override suspend fun updateSeller(sellerId: Int, seller: Seller) {
        dbQuery {
            SellerTable.update({
                SellerTable.id.eq(sellerId)
            }) {

                it[id] = seller.id
                it[title] = seller.title
                it[description] = seller.description!!
                it[logo] = seller.logo!!
                it[banner] = seller.banner!!
                it[sellerCategoryId] = seller.seller_category_id
                it[resultCategoryId] = seller.result_category_id
                it[foodCategoryId] = seller.food_category_id!!
                it[stateId] = seller.state_id
                it[cityId] = seller.city_id
                it[location] = seller.location
                it[resultsId] = seller.results_id
                it[isOpen] = seller.is_open!!
                it[rating] = seller.rating!!
                it[voteCount] = seller.vote_count!!
                it[deliveryFee] = seller.delivery_fee
                it[deliveryDuration] = seller.delivery_duration
                it[userId] = seller.user_id
            }
        }
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

    override suspend fun deleteSellersByCityId(cityId: Int) {
        dbQuery {
            SellerTable.deleteWhere {
                SellerTable.cityId.eq(cityId)
            }
        }
    }

    override suspend fun deleteSellerByLocation(location: String?) {
        dbQuery {
            SellerTable.deleteWhere {
                SellerTable.location.eq(location!!)
            }
        }
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
            food_category_id = row[SellerTable.foodCategoryId],
            state_id = row[SellerTable.stateId],
            city_id = row[SellerTable.cityId],
            location = row[SellerTable.location],
            results_id = row[SellerTable.resultsId],
            is_open = row[SellerTable.isOpen],
            rating = row[SellerTable.rating],
            vote_count = row[SellerTable.voteCount],
            delivery_fee = row[SellerTable.deliveryFee],
            delivery_duration = row[SellerTable.deliveryDuration],
            user_id = row[SellerTable.userId]
        )
    }
}