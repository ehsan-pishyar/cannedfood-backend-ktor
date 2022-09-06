package com.example.repository.impl

import com.example.models.*
import com.example.models.responses.ResultCommentResponse
import com.example.models.responses.ResultDetailsResponse
import com.example.models.responses.ResultResponse
import com.example.models.responses.SellerResponse
import com.example.repository.ResultsRepository
import com.example.tables.*
import com.example.tables.DatabaseFactory.dbQuery
import com.example.utils.ErrorCode
import com.example.utils.ServiceResult
import com.example.utils.randomIdGenerator
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class ResultsRepositoryImpl : ResultsRepository {

    override suspend fun insertResult(result: Results): ServiceResult<Results> {
        return try {
            dbQuery {
                ResultsTable.insert {
                    it[id] = randomIdGenerator()
                    it[sellerId] = result.seller_id
                    it[title] = result.title
                    it[description] = result.description
                    it[foodCategoryId] = result.food_category_id
                    it[imagePath] = result.image_path
                    it[price] = result.price
                    it[discount] = result.discount
                    it[prepareDuration] = result.prepare_duration
                    it[dateCreated] = result.date_created
                }
                    .resultedValues?.single().let { ServiceResult.Success(rowToResults(it)!!) }
            }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    /**
     * به خاطر اینکه هم جدول seller دارای ستون foodCategoryId هستش و هم جدول result این ستون رو داره، و اینکه این ستون چون رفرنس میده
     * به جدول FoodCategoryTable، واسه همین اگه بخوایم بدون شرط و به صورت عادی innerJoin بدیم این 3 تا جدول رو، بهمون خطا میده که
     * دو تا جدول result و seller دارن به جدول foodCategory رفرنس میدن و این کار امکان پذیر نیست. واسه همین باید توی innerJoin، شرط هم بذاریم
     * تا این خطا برطرف بشه
     */
    override suspend fun getResults(): ServiceResult<List<ResultResponse?>> {
        return try {
            dbQuery {
                ResultsTable
                    .innerJoin(SellerTable, {sellerId}, {id}) // Join SellerTable Where ResultsTable.sellerId eq SellerTable.id
                    .innerJoin(FoodCategoryTable, {ResultsTable.foodCategoryId}, {id}) // Join FoodCategoryTable where ResultsTable.foodCategoryId eq FoodCategoryTable.id
                    .selectAll()
                    .orderBy(ResultsTable.dateCreated to SortOrder.ASC)
                    .map { rowToResultResponse(it) }
            }.let { ServiceResult.Success(it) }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getResultDetails(resultId: Long): ServiceResult<ResultDetailsResponse?> {
        lateinit var resultDetailsResponse: ResultDetailsResponse
        return try {
            dbQuery {
                val result = selectResultById(resultId)
                val seller = selectSeller(result.seller_id)
                val foodCategory = selectFoodCategory(result.food_category_id)
                val comments = selectComments(resultId)

                resultDetailsResponse = ResultDetailsResponse(
                    id = result.id,
                    title = result.title,
                    description = result.description,
                    image_path = result.image_path,
                    price = result.price,
                    discount = result.discount,
                    prepare_duration = result.prepare_duration,
                    seller = seller,
                    food_category = foodCategory!!.title,
                    rating = result.rating,
                    comments = comments,
                    date_created = result.date_created
                )
            }.let { ServiceResult.Success(resultDetailsResponse) }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getResultsByTitle(resultTitle: String): ServiceResult<List<ResultResponse?>> {
        return try {
            dbQuery {
                ResultsTable
                    .innerJoin(SellerTable, {sellerId}, {id})
                    .innerJoin(FoodCategoryTable, {ResultsTable.foodCategoryId}, {id})
                    .select {
                        (ResultsTable.title like "$resultTitle%")
                    }
                    .orderBy(ResultsTable.dateCreated to SortOrder.DESC)
                    .map { rowToResultResponse(it) }
            }.let { ServiceResult.Success(it) }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getResultsByDescription(description: String?): ServiceResult<List<ResultResponse?>> {
        return try {
            dbQuery {
                ResultsTable
                    .innerJoin(SellerTable, {sellerId}, {id})
                    .innerJoin(FoodCategoryTable, {ResultsTable.foodCategoryId}, {id})
                    .select {
                        (ResultsTable.description like "$description%")
                    }
                    .orderBy(ResultsTable.dateCreated to SortOrder.DESC)
                    .map { rowToResultResponse(it) }
            }.let { ServiceResult.Success(it) }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getResultsBySellerId(sellerId: Long): ServiceResult<List<ResultResponse?>> {
        return try {
            dbQuery {
                ResultsTable
                    .innerJoin(SellerTable, { ResultsTable.sellerId }, {id})
                    .innerJoin(FoodCategoryTable, {ResultsTable.foodCategoryId}, {id})
                    .select {
                        (ResultsTable.sellerId eq sellerId)
                    }
                    .orderBy(ResultsTable.dateCreated to SortOrder.DESC)
                    .map { rowToResultResponse(it) }
            }.let { ServiceResult.Success(it) }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getResultsBySellerCategoryId(sellerCategoryId: Int): ServiceResult<List<ResultResponse?>> {
        return try {
            dbQuery {
                ResultsTable.select {
                    (ResultsTable.sellerCategoryId eq sellerCategoryId)
                }
                    .orderBy(ResultsTable.dateCreated to SortOrder.DESC)
                    .map { rowToResultResponse(it) }
            }.let { ServiceResult.Success(it) }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }

    }

    override suspend fun getResultsByResultCategoryId(resultCategoryId: Int): ServiceResult<List<ResultResponse?>> {
        return try {
            dbQuery {
                ResultsTable.select {
                    (ResultsTable.resultCategoryId eq resultCategoryId)
                }
                    .map { rowToResultResponse(it) }
            }.let { ServiceResult.Success(it) }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getResultsByFoodCategoryId(foodCategoryId: Int): ServiceResult<List<ResultResponse?>> {
        return try {
            dbQuery {
                ResultsTable
                    .innerJoin(SellerTable, {sellerId}, {id})
                    .innerJoin(FoodCategoryTable, {ResultsTable.foodCategoryId}, {id})
                    .select {
                        (ResultsTable.foodCategoryId eq foodCategoryId)
                    }
                    .orderBy(ResultsTable.dateCreated to SortOrder.DESC)
                    .map { rowToResultResponse(it) }
            }.let { ServiceResult.Success(it) }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getResultsByPrice(price: Long): ServiceResult<List<ResultResponse?>> {
        return try {
            dbQuery {
                ResultsTable
                    .innerJoin(SellerTable, {sellerId}, {id})
                    .innerJoin(FoodCategoryTable, {ResultsTable.foodCategoryId}, {id})
                    .select {
                        (ResultsTable.price lessEq price)
                    }
                    .orderBy(ResultsTable.dateCreated to SortOrder.DESC)
                    .map { rowToResultResponse(it) }
            }.let { ServiceResult.Success(it) }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getResultsByRating(rating: Double): ServiceResult<List<ResultResponse?>> {
        TODO("Not yet implemented")
    }

    override suspend fun getResultsByVoteCount(voteCount: Long): ServiceResult<List<ResultResponse?>> {
        TODO("Not yet implemented")
    }

    override suspend fun getResultsByDiscount(discount: Int): ServiceResult<List<ResultResponse?>> {
        return try {
            dbQuery {
                ResultsTable
                    .innerJoin(SellerTable, {sellerId}, {id})
                    .innerJoin(FoodCategoryTable, {ResultsTable.foodCategoryId}, {id})
                    .select {
                        (ResultsTable.title greaterEq discount)
                    }
                    .orderBy(ResultsTable.dateCreated to SortOrder.DESC)
                    .map { rowToResultResponse(it) }
            }.let { ServiceResult.Success(it) }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getResultsByDateAdded(dateAdded: String): ServiceResult<List<ResultResponse?>> {
        return try {
            dbQuery {
                ResultsTable
                    .innerJoin(SellerTable, {sellerId}, {id})
                    .innerJoin(FoodCategoryTable, {ResultsTable.foodCategoryId}, {id})
                    .select {
                        (ResultsTable.dateCreated like dateAdded)
                    }
                    .orderBy(ResultsTable.dateCreated to SortOrder.DESC)
                    .map { rowToResultResponse(it) }
            }.let { ServiceResult.Success(it) }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getResultsByPrepareDuration(minutes: Int): ServiceResult<List<ResultResponse?>> {
        return try {
            dbQuery {
                ResultsTable
                    .innerJoin(SellerTable, {sellerId}, {id})
                    .innerJoin(FoodCategoryTable, {ResultsTable.foodCategoryId}, {id})
                    .select {
                        (ResultsTable.prepareDuration lessEq minutes)
                    }
                    .orderBy(ResultsTable.dateCreated to SortOrder.DESC)
                    .map { rowToResultResponse(it) }
            }.let { ServiceResult.Success(it) }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun updateResultById(resultId: Long, result: Results): ServiceResult<Results> {
        return try {
            dbQuery {
                ResultsTable.update({
                    (ResultsTable.id eq resultId)
                }) {
                    it[id] = resultId
                    it[sellerId] = result.seller_id
                    it[title] = result.title
                    it[description] = result.description
                    it[foodCategoryId] = result.food_category_id
                    it[imagePath] = result.image_path
                    it[price] = result.price
                    it[discount] = result.discount
                    it[prepareDuration] = result.prepare_duration
                    it[dateCreated] = result.date_created
                }
                ServiceResult.Success(selectResultById(resultId))
            }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun deleteResultById(sellerId: Long, resultId: Long): ServiceResult<List<ResultResponse?>> {
        return try {
            dbQuery {
                ResultsTable.deleteWhere {
                    (ResultsTable.id eq resultId)
                }
                ServiceResult.Success(getResultResponseBySellerId(sellerId))
            }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun deleteResults(sellerId: Long): ServiceResult<List<ResultResponse?>> {
        return try {
            dbQuery {
                ResultsTable.deleteAll()
                ServiceResult.Success(getResultResponseBySellerId(sellerId))
            }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
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
            prepare_duration = row[ResultsTable.prepareDuration]!!,
            date_created = row[ResultsTable.dateCreated]
        )
    }

    private fun rowToResultResponse(row: ResultRow?): ResultResponse? {
        if (row == null) return null

        return ResultResponse(
            id = row[ResultsTable.id],
            title = row[ResultsTable.title],
            description = row[ResultsTable.description]!!,
            image_path = row[ResultsTable.imagePath],
            price = row[ResultsTable.price],
            discount = row[ResultsTable.discount]!!,
            prepare_duration = row[ResultsTable.prepareDuration]!!,
            seller = row[SellerTable.title],
            food_category = row[FoodCategoryTable.title],
            date_created = row[ResultsTable.dateCreated]
        )
    }

    private fun rowToSellerResponse(row: ResultRow?): SellerResponse? {
        if (row == null) return null

        return SellerResponse(
            id = row[SellerTable.id],
            title = row[SellerTable.title],
            description = row[SellerTable.description]!!,
            logo = row[SellerTable.logo]!!,
            banner = row[SellerTable.banner]!!,
            delivery_fee = row[SellerTable.deliveryFee]!!,
            delivery_duration = row[SellerTable.deliveryDuration]!!
        )
    }

    private fun rowToFoodCategory(row: ResultRow?): FoodCategory? {
        if (row == null) return null

        return FoodCategory(
            id = row[FoodCategoryTable.id],
            title = row[FoodCategoryTable.title],
            image_path = row[FoodCategoryTable.imagePath]!!,
            result_category_id = row[FoodCategoryTable.resultCategoryId]
        )
    }

    private fun rowToResultComments(row: ResultRow?): ResultCommentResponse? {
        if (row == null) return null

        return ResultCommentResponse(
            from = row[UserTable.email],
            message = row[ResultCommentTable.message],
            date_created = row[ResultCommentTable.dateCreated]
        )
    }

    private fun selectSeller(sellerId: Long): SellerResponse {
        return transaction {
            SellerTable.select {
                (SellerTable.id eq sellerId)
            }
                .map { rowToSellerResponse(it)!! }
                .single()
        }
    }

    private fun selectFoodCategory(foodCategoryId: Int): FoodCategory? {
        return transaction {
            FoodCategoryTable.select {
                (FoodCategoryTable.id eq foodCategoryId)
            }
                .map { rowToFoodCategory(it) }
                .singleOrNull()
        }
    }

    private fun selectComments(resultId: Long): List<ResultCommentResponse?> {
        return transaction {
            ResultCommentTable
                .innerJoin(CustomerTable, {fromCustomerId}, {id})
                .innerJoin(UserTable, {CustomerTable.userId}, {id})
                .select {
                    (ResultCommentTable.toResultId eq resultId)
                }
                .map { rowToResultComments(it) }
        }
    }

    private fun selectResultById(id: Long): Results {
        return transaction {
            ResultsTable.select {
                (ResultsTable.id eq id)
            }
                .map { rowToResults(it)!! }
                .single()
        }
    }

    private fun getResultResponseBySellerId(sellerId: Long): List<ResultResponse?> {
        return transaction {
            ResultsTable
                .innerJoin(SellerTable, { ResultsTable.sellerId }, {id})
                .innerJoin(FoodCategoryTable, {ResultsTable.foodCategoryId}, {id})
                .select {
                    (ResultsTable.sellerId eq sellerId)
                }
                .orderBy(ResultsTable.dateCreated to SortOrder.DESC)
                .map { rowToResultResponse(it) }
        }
    }
}