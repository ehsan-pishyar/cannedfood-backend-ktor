package com.example.datasource

import com.example.models.Seller
import com.example.repository.SellerRepository
import com.example.tables.DatabaseFactory.dbQuery
import com.example.tables.CustomerTable
import com.example.tables.SellerTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like

class SellerRepositoryImpl : SellerRepository {

    override suspend fun insertSeller(seller: Seller) {
        TODO("Not yet implemented")
    }

    override suspend fun getSellers(): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByTitle(sellerTitle: String?): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByDescription(description: String?): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByStateId(stateId: Int): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByCityId(cityId: Int): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByLocationTitle(locationTitle: String?): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByResultsId(resultId: Int): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByResultsTitle(resultTitle: String?): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersBySellerCategoryId(sellerCategoryId: Int): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByResultCategoryId(resultCategoryId: Int): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByFoodCategoryId(foodCategoryId: Int): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByOpenStatus(isOpen: Boolean): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByDeliveryDuration(minutes: Int): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByDeliveryFee(fee: Int): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun updateSeller(sellerId: Int, seller: Seller) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSellerById(sellerId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSellersByRating(rating: Double) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSellersByStateId(stateId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSellersByCityId(cityId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSellerByLocation(location: String?) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSellers() {
        TODO("Not yet implemented")
    }

    private fun rowToSeller(row: ResultRow?): Seller? {
        if (row == null) return null

        return Seller(
            id = row[SellerTable.id],
            user_id = row[SellerTable.userId],
            title = row[SellerTable.title],
            description = row[SellerTable.description],
            logo = row[SellerTable.logo],
            banner = row[SellerTable.banner],
            is_open = row[SellerTable.isOpen],
            rating = row[SellerTable.rating],
            vote_count = row[SellerTable.voteCount],
            delivery_fee = row[SellerTable.deliveryFee],
            delivery_duration = row[SellerTable.deliveryDuration],
        )
    }
}