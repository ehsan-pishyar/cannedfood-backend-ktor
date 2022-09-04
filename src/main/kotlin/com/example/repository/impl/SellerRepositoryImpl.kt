package com.example.repository.impl

import com.example.models.*
import com.example.models.responses.*
import com.example.repository.SellerRepository
import com.example.tables.*
import com.example.tables.DatabaseFactory.dbQuery
import com.example.utils.ErrorCode
import com.example.utils.ServiceResult
import com.example.utils.randomIdGenerator
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class SellerRepositoryImpl : SellerRepository {
    override suspend fun insertSeller(seller: Seller): ServiceResult<Seller> {
        return try {
            dbQuery {
                SellerTable.insert {
                    it[id] = randomIdGenerator()
                    it[userId] = seller.user_id
                    it[title] = seller.title
                    it[description] = seller.description
                    it[logo] = seller.logo
                    it[banner] = seller.banner
                    it[stateId] = seller.state_id
                    it[cityId] = seller.city_id
                    it[locationId] = seller.location_id
                    it[sellerCategoryId] = seller.seller_category_id
                    it[resultCategoryId] = seller.result_category_id
                    it[foodCategoryId] = seller.food_category_id
                    it[deliveryFee] = seller.delivery_fee
                    it[deliveryDuration] = seller.delivery_duration
                    it[phoneNumber] = seller.phone_number
                }
                    .resultedValues?.single().let {
                        ServiceResult.Success(rowToSeller(it)!!)
                    }
            }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.SELLER_EXISTS)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getSellers(): ServiceResult<List<SellerResponse?>> {
        return try {
            dbQuery {
                SellerTable.selectAll()
                    .orderBy(SellerTable.dateCreated to SortOrder.DESC)
                    .map { rowToSellerResponse(it) }
            }.let {
                ServiceResult.Success(it)
            }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getSellerDetails(sellerId: Long): ServiceResult<SellerDetailsResponse> {
        lateinit var sellerDetailsResponse: SellerDetailsResponse
        dbQuery {
            val seller = transaction {
                SellerTable.select {
                    (SellerTable.id eq sellerId)
                }
                    .map { rowToSeller(it) }
                    .single()
            }

            val location = getLocation(seller!!.location_id)
            val results = getResults(seller.id)
            val comments = getComments(seller.id)

            sellerDetailsResponse = SellerDetailsResponse(
                id = seller.id,
                title = seller.title,
                description = seller.description,
                logo = seller.logo,
                banner = seller.banner,
                location = location,
                results = results,
                comments = comments,
                delivery_fee = seller.delivery_fee,
                delivery_duration = seller.delivery_duration,
                phone_number = seller.phone_number,
                date_created = seller.date_created
            )
        }
        return sellerDetailsResponse.let {
            ServiceResult.Success(it)
        }
    }

    override suspend fun getSellersByTitle(sellerTitle: String?): ServiceResult<List<SellerResponse?>> {
        return try {
            dbQuery {
                SellerTable.select {
                    (SellerTable.title like "$sellerTitle%")
                }
                    .orderBy(SellerTable.id to SortOrder.ASC)
                    .map { rowToSellerResponse(it) }
            }.let {
                ServiceResult.Success(it)
            }
        } catch (e: Exception) {
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getSellersByDescription(description: String?): ServiceResult<List<SellerResponse?>> {
        return try {
            dbQuery {
                SellerTable.select {
                    (SellerTable.description like "$description%")
                }
                    .orderBy(SellerTable.id to SortOrder.ASC)
                    .map { rowToSellerResponse(it) }
            }.let {
                ServiceResult.Success(it)
            }
        } catch (e: Exception) {
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getSellersByStateId(stateId: Int): ServiceResult<List<SellerResponse?>> {
        return try {
            dbQuery {
                SellerTable.select {
                    (SellerTable.stateId eq stateId)
                }
                    .orderBy(SellerTable.id to SortOrder.ASC)
                    .map { rowToSellerResponse(it) }
            }.let {
                ServiceResult.Success(it)
            }
        } catch (e: Exception) {
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getSellersByCityId(cityId: Int): ServiceResult<List<SellerResponse?>> {
        return try {
            dbQuery {
                SellerTable.select {
                    (SellerTable.cityId eq cityId)
                }
                    .orderBy(SellerTable.id to SortOrder.ASC)
                    .map { rowToSellerResponse(it) }
            }.let {
                ServiceResult.Success(it)
            }
        } catch (e: Exception) {
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getSellersByLocationId(locationId: Long): ServiceResult<List<SellerResponse?>> {
        return try {
            dbQuery {
                SellerTable.select {
                    (SellerTable.locationId eq locationId)
                }
                    .orderBy(SellerTable.id to SortOrder.ASC)
                    .map { rowToSellerResponse(it) }
            }.let {
                ServiceResult.Success(it)
            }
        } catch (e: Exception) {
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getSellersByLocationTitle(locationTitle: String?): ServiceResult<List<SellerResponse?>> {
        return try {
            dbQuery {
                val location = getLocationByTitle(locationTitle)

                transaction {
                    SellerTable.select {
                        (SellerTable.locationId eq location?.id!!)
                    }
                        .orderBy(SellerTable.dateCreated to SortOrder.DESC)
                        .map { rowToSellerResponse(it) }
                }.let {
                    ServiceResult.Success(it)
                }
            }
        } catch (e: Exception) {
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getSellerByResultId(resultId: Long): ServiceResult<SellerResponse?> {
        return try {
            dbQuery {
                val result = getResultById(resultId)

                transaction {
                    SellerTable.select {
                        SellerTable.id eq result.seller_id
                    }
                        .map { rowToSellerResponse(it) }
                        .singleOrNull()
                }.let {
                    ServiceResult.Success(it!!)
                }
            }
        } catch (e: Exception) {
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getSellersBySellerCategoryId(sellerCategoryId: Int): ServiceResult<List<SellerResponse?>> {
        return try {
            dbQuery {
                SellerTable.select {
                    (SellerTable.sellerCategoryId eq sellerCategoryId)
                }
                    .orderBy(SellerTable.dateCreated to SortOrder.DESC)
                    .map { rowToSellerResponse(it) }
            }.let {
                ServiceResult.Success(it)
            }
        } catch (e: Exception) {
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getSellersByResultCategoryId(resultCategoryId: Int): ServiceResult<List<SellerResponse?>> {
        return try {
            dbQuery {
                SellerTable.select {
                    (SellerTable.resultCategoryId eq resultCategoryId)
                }
                    .orderBy(SellerTable.dateCreated to SortOrder.DESC)
                    .map { rowToSellerResponse(it) }
            }.let {
                ServiceResult.Success(it)
            }
        } catch (e: Exception) {
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getSellersByFoodCategoryId(foodCategoryId: Int): ServiceResult<List<SellerResponse?>> {
        return try {
            dbQuery {
                SellerTable.select {
                    (SellerTable.foodCategoryId eq foodCategoryId)
                }
                    .map { rowToSellerResponse(it) }
            }.let {
                ServiceResult.Success(it)
            }
        } catch (e: Exception) {
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getSellersByOpenStatus(isOpen: Boolean): ServiceResult<List<SellerResponse?>> {
        return try {
            dbQuery {
                val status = transaction {
                    SellerOpenStatusTable.select {
                        (SellerOpenStatusTable.isOpen eq isOpen)
                    }
                        .map { rowToSellerOpenStatus(it)!! }
                        .single()
                }

                transaction {
                    SellerTable.select {
                        (SellerTable.id eq status.seller_id)
                    }
                        .orderBy(SellerTable.dateCreated to SortOrder.DESC)
                        .map { rowToSellerResponse(it) }
                }.let {
                    ServiceResult.Success(it)
                }
            }
        } catch (e: Exception) {
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getSellersByDeliveryDuration(minutes: Int): ServiceResult<List<SellerResponse?>> {
        return try {
            dbQuery {
                SellerTable.select {
                    (SellerTable.deliveryDuration lessEq minutes)
                }
                    .orderBy(SellerTable.deliveryDuration to SortOrder.ASC)
                    .map { rowToSellerResponse(it) }
            }.let {
                ServiceResult.Success(it)
            }
        } catch (e: Exception) {
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getSellersByDeliveryFee(fee: Long): ServiceResult<List<SellerResponse?>> {
        return try {
            dbQuery {
                SellerTable.select {
                    (SellerTable.deliveryFee lessEq fee)
                }
                    .orderBy(SellerTable.deliveryDuration to SortOrder.ASC)
                    .map { rowToSellerResponse(it) }
            }.let {
                ServiceResult.Success(it)
            }
        } catch (e: Exception) {
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun updateSeller(sellerId: Long, seller: Seller): ServiceResult<Seller> {
        return try {
            dbQuery {
                SellerTable.update({
                    SellerTable.id eq sellerId
                }) {
                    it[id] = sellerId
                    it[title] = seller.title
                    it[description] = seller.description
                    it[logo] = seller.logo
                    it[banner] = seller.banner
                    it[stateId] = seller.state_id
                    it[cityId] = seller.city_id
                    it[locationId] = seller.location_id
                    it[sellerCategoryId] = seller.seller_category_id
                    it[resultCategoryId] = seller.result_category_id
                    it[foodCategoryId] = seller.food_category_id
                    it[deliveryFee] = seller.delivery_fee
                    it[deliveryDuration] = seller.delivery_duration
                    it[phoneNumber] = seller.phone_number
                }

                transaction {
                    SellerTable.select {
                        (SellerTable.id eq sellerId)
                    }
                        .map { rowToSeller(it) }
                        .single()
                }.let {
                    ServiceResult.Success(it!!)
                }
            }
        } catch (e: Exception) {
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun deleteSellerById(sellerId: Long): ServiceResult<List<SellerResponse?>> {
        return try {
            dbQuery {
                SellerTable.deleteWhere {
                    (SellerTable.id eq sellerId)
                }

                transaction {
                    SellerTable.selectAll()
                        .map { rowToSellerResponse(it) }
                }.let {
                    ServiceResult.Success(it)
                }
            }
        } catch (e: Exception) {
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun deleteSellers(): ServiceResult<List<SellerResponse?>> {
        return try {
            dbQuery {
                SellerTable.deleteAll()

                transaction {
                    SellerTable.selectAll()
                        .map { rowToSellerResponse(it) }
                }.let {
                    ServiceResult.Success(it)
                }
            }
        } catch (e: Exception) {
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    private fun rowToSeller(row: ResultRow?): Seller? {
        if (row == null) return null

        return Seller(
            id = row[SellerTable.id],
            user_id = row[SellerTable.userId],
            title = row[SellerTable.title],
            description = row[SellerTable.description]!!,
            logo = row[SellerTable.logo]!!,
            banner = row[SellerTable.banner]!!,
            state_id = row[SellerTable.stateId],
            city_id = row[SellerTable.cityId],
            location_id = row[SellerTable.locationId],
            seller_category_id = row[SellerTable.sellerCategoryId],
            result_category_id = row[SellerTable.resultCategoryId],
            food_category_id = row[SellerTable.foodCategoryId],
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
            delivery_fee = row[SellerTable.deliveryFee],
            delivery_duration = row[SellerTable.deliveryDuration],
        )
    }

    private fun rowToResults(row: ResultRow?): Results? {
        if (row == null) return null

        return Results(
            id = row[ResultsTable.id],
            seller_id = row[ResultsTable.sellerId],
            title = row[ResultsTable.title]
        )
    }

    private fun rowToResultResponse(row: ResultRow?): ResultResponse? {
        if (row == null) return null

        return ResultResponse(
            id = row[ResultsTable.id],
            seller = row[SellerTable.title],
            title = row[ResultsTable.title],
            description = row[ResultsTable.description]!!,
            food_category = row[FoodCategoryTable.title],
            image_path = row[ResultsTable.imagePath],
            price = row[ResultsTable.price],
            discount = row[ResultsTable.discount]!!,
            prepare_duration = row[ResultsTable.prepareDuration]!!,
            date_created = row[ResultsTable.dateCreated]
        )
    }

    private fun rowToLocationResponse(row: ResultRow?): LocationResponse? {
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

    private fun rowToSellerCommentsResponse(row: ResultRow?): SellerCommentResponse? {
        if (row == null) return null

        return SellerCommentResponse(
            from = row[CustomerTable.firstName],
            message = row[SellerCommentTable.message]
        )
    }
    
    private fun rowToSellerOpenStatus(row: ResultRow?): SellerOpenStatus? {
        if (row == null) return null

        return SellerOpenStatus(
            id = row[SellerOpenStatusTable.id],
            seller_id = row[SellerOpenStatusTable.sellerId],
            is_open = row[SellerOpenStatusTable.isOpen]
        )
    }

    private fun getLocation(locationId: Long): LocationResponse {
        return transaction {
            (LocationTable innerJoin CityTable innerJoin StateTable).select {
                (LocationTable.id eq locationId)
            }
                .map { rowToLocationResponse(it)!! }
                .single()
        }
    }

    private fun getResults(sellerId: Long): List<ResultResponse?> {
        return transaction {
            ResultsTable
                .innerJoin(SellerTable, {ResultsTable.sellerId}, {id})
                .innerJoin(FoodCategoryTable, {ResultsTable.foodCategoryId}, {id})
            .select {
                (ResultsTable.sellerId eq sellerId)
            }
                .orderBy(ResultsTable.dateCreated to SortOrder.DESC)
                .map { rowToResultResponse(it) }
        }
    }

    private fun getResultById(resultId: Long): Results {
        return transaction {
            (ResultsTable).select {
                (ResultsTable.id eq resultId)
            }
                .map { rowToResults(it)!! }
                .single()
        }
    }

    private fun getComments(sellerId: Long): List<SellerCommentResponse?> {
        return transaction {
            (SellerCommentTable innerJoin CustomerTable).select {
                (SellerCommentTable.toSellerId eq sellerId)
            }
                .orderBy(SellerCommentTable.dateCreated to SortOrder.DESC)
                .map { rowToSellerCommentsResponse(it) }
        }
    }

    private fun getLocationByTitle(locationTitle: String?): LocationResponse?{
        return transaction {
            (LocationTable innerJoin CityTable innerJoin StateTable).select {
                (LocationTable.title like "$locationTitle%")
            }
                .map {
                    rowToLocationResponse(it)!!
                }
                .singleOrNull()
        }
    }
}