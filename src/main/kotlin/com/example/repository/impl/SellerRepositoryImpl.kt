package com.example.repository.impl

import com.example.models.Results
import com.example.models.Seller
import com.example.models.responses.LocationResponse
import com.example.models.responses.SellerResponse
import com.example.repository.SellerRepository
import com.example.tables.*
import com.example.tables.DatabaseFactory.dbQuery
import com.example.utils.ErrorCode
import com.example.utils.ServiceResult
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*

class SellerRepositoryImpl : SellerRepository {
    override suspend fun insertSeller(seller: Seller): ServiceResult<Seller> {
        return try {
            dbQuery {
                SellerTable.insert {
                    it[userId] = seller.user_id
                    it[title] = seller.title
                    it[description] = seller.description
                    it[logo] = seller.logo
                    it[banner] = seller.banner
                    it[stateId] = seller.state_id
                    it[cityId] = seller.city_id
                    it[deliveryFee] = seller.delivery_fee
                    it[deliveryDuration] = seller.delivery_duration
                    it[phoneNumber] = seller.phone_number
                    it[dateCreated] = seller.date_created
                }
                    .resultedValues?.singleOrNull()?.let {
                        ServiceResult.Success(rowToSeller(it)!!)
                    } ?: ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.SELLER_EXISTS)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getSellers(): ServiceResult<List<SellerResponse?>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellerById(sellerId: Long): ServiceResult<SellerResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByTitle(sellerTitle: String?): ServiceResult<List<SellerResponse?>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByDescription(description: String?): ServiceResult<List<SellerResponse?>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByStateId(stateId: Int): ServiceResult<List<SellerResponse?>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByCityId(cityId: Int): ServiceResult<List<SellerResponse?>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByLocationId(locationId: Long): ServiceResult<List<SellerResponse?>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByLocationTitle(locationTitle: String?): ServiceResult<List<SellerResponse?>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByResultId(resultId: Long): ServiceResult<SellerResponse?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByResultTitle(resultTitle: String?): ServiceResult<List<SellerResponse?>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersBySellerCategoryId(sellerCategoryId: Int): ServiceResult<List<SellerResponse?>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByResultCategoryId(resultCategoryId: Int): ServiceResult<List<SellerResponse?>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByFoodCategoryId(foodCategoryId: Int): ServiceResult<List<SellerResponse?>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByOpenStatus(isOpen: Boolean): ServiceResult<List<SellerResponse?>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByDeliveryDuration(minutes: Int): ServiceResult<List<SellerResponse?>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByDeliveryFee(fee: Long): ServiceResult<List<SellerResponse?>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateSeller(sellerId: Long, seller: Seller): ServiceResult<SellerResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSeller(sellerId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSellers() {
        TODO("Not yet implemented")
    }

    private fun rowToSeller(row: ResultRow?): Seller? {
        if (row == null) return null

        return Seller(
            id = row[SellerTable.id],
            title = row[SellerTable.title],
            description = row[SellerTable.description]!!,
            logo = row[SellerTable.logo]!!,
            banner = row[SellerTable.banner]!!,
            state_id = row[SellerTable.stateId],
            city_id = row[SellerTable.cityId],
            delivery_fee = row[SellerTable.deliveryFee]!!,
            delivery_duration = row[SellerTable.deliveryDuration]!!,
            phone_number = row[SellerTable.phoneNumber]!!,
            date_created = row[SellerTable.dateCreated]
        )
    }

    private fun rowToSellerResponse(row: ResultRow?): SellerResponse? {
        if (row == null) return null

        return SellerResponse(
            id = row[SellerTable.id],
            title = row[SellerTable.title],
            description = row[SellerTable.description],
            logo = row[SellerTable.logo],
            banner = row[SellerTable.banner],
            rating = row[SellerRatingTable.rating].toDouble(),
            vote_count = row[SellerRatingTable.id.count()],
            delivery_fee = row[SellerTable.deliveryFee],
            delivery_duration = row[SellerTable.deliveryDuration],
            phone_number = row[SellerTable.phoneNumber],
        )
    }

    private fun rowToResults(row: ResultRow?): Results? {
        if (row == null) return null

        return Results(
            id = row[ResultsTable.id],
            seller_id = row[ResultsTable.sellerId],
            title = row[ResultsTable.title],
            description = row[ResultsTable.description]!!,
            food_category_id = row[ResultsTable.foodCategoryId],
            image_path = row[ResultsTable.imagePath],
            price = row[ResultsTable.price],
            discount = row[ResultsTable.discount]!!,
            date_created = row[ResultsTable.dateCreated],
            prepare_duration = row[ResultsTable.prepareDuration]!!
        )
    }

    private fun rowToLocation(row: ResultRow?): LocationResponse? {
        if (row == null) return null

        return LocationResponse(
            id = row[LocationTable.id],
            title = row[LocationTable.title],
            lat = row[LocationTable.lat],
            lon = row[LocationTable.lon],
            city = row[CityTable.title],
            state = row[StateTable.title]
        )
    }
}