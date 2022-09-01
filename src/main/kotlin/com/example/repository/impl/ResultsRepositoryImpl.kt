package com.example.repository.impl

import com.example.models.FoodCategory
import com.example.models.ResultCategory
import com.example.models.Results
import com.example.models.Seller
import com.example.models.responses.ResultResponse
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
                    .resultedValues?.singleOrNull()?.let {
                        ServiceResult.Success(rowToResults(it)!!)
                    } ?: ServiceResult.Error(ErrorCode.DATABASE_ERROR)
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
            }.let {
                ServiceResult.Success(it)
            }
        } catch (e: Exception) {
            println(e)
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    // TODO: 9/1/2022 Use ResultDetailsResponse for result details
    override suspend fun getResultById(resultId: Long): ServiceResult<ResultResponse?> {
        return try {
            dbQuery {
                ResultsTable
                    .innerJoin(SellerTable, {sellerId}, {id})
                    .innerJoin(FoodCategoryTable, {ResultsTable.foodCategoryId}, {id})
                    .select {
                        (ResultsTable.id eq resultId)
                    }
                    .map { rowToResultResponse(it) }
                    .single()
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

    override suspend fun getResultsByDescription(description: String?): ServiceResult<List<ResultResponse?>> {
        return try {
            dbQuery {
                ResultsTable
                    .innerJoin(SellerTable, {sellerId}, {id})
                    .innerJoin(FoodCategoryTable, {ResultsTable.foodCategoryId}, {id})
                    .select {
                        (ResultsTable.title like "$description%")
                    }
                    .orderBy(ResultsTable.dateCreated to SortOrder.DESC)
                    .map { rowToResultResponse(it) }
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

    override suspend fun getResultsBySellerCategoryId(sellerCategoryId: Int): ServiceResult<List<ResultResponse?>> {
        return try {
            dbQuery {
                val resultCategory = transaction {
                    ResultCategoryTable.select {
                        (ResultCategoryTable.sellerCategoryId eq sellerCategoryId)
                    }
                        .map { rowToResultCategory(it) }
                        .single()
                }

                val foodCategory = transaction {
                    FoodCategoryTable.select {
                        (FoodCategoryTable.resultCategoryId eq resultCategory?.id!!)
                    }
                        .map { rowToFoodCategory(it) }
                        .single()
                }

                transaction {
                    ResultsTable.select {
                        (ResultsTable.foodCategoryId eq foodCategory?.id!!)
                    }
                        .orderBy(ResultsTable.dateCreated to SortOrder.DESC)
                        .map { rowToResultResponse(it) }
                }.let {
                    ServiceResult.Success(it)
                }
            }
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
                val foodCategory = transaction {
                    FoodCategoryTable.select {
                        (FoodCategoryTable.resultCategoryId eq resultCategoryId)
                    }
                        .map { rowToFoodCategory(it) }
                        .single()
                }

                transaction {
                    ResultsTable.select {
                        (ResultsTable.foodCategoryId eq foodCategory?.id!!)
                    }
                        .orderBy(ResultsTable.dateCreated to SortOrder.DESC)
                        .map { rowToResultResponse(it) }
                }.let {
                    ServiceResult.Success(it)
                }
            }
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
                }

                transaction {
                    ResultsTable.select {
                        (ResultsTable.id eq resultId)
                    }
                        .map { rowToResults(it)!! }
                        .single()
                }.let {
                    ServiceResult.Success(it)
                }
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

                transaction {
                    ResultsTable
                        .innerJoin(SellerTable, { ResultsTable.sellerId }, {id})
                        .innerJoin(FoodCategoryTable, {ResultsTable.foodCategoryId}, {id})
                        .select {
                        (ResultsTable.sellerId eq sellerId)
                    }
                        .orderBy(ResultsTable.dateCreated to SortOrder.DESC)
                        .map { rowToResultResponse(it) }
                }
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

    override suspend fun deleteResults(sellerId: Long): ServiceResult<List<ResultResponse?>> {
        return try {
            dbQuery {
                ResultsTable.deleteAll()

                transaction {
                    ResultsTable
                        .innerJoin(SellerTable, { ResultsTable.sellerId }, {id})
                        .innerJoin(FoodCategoryTable, {ResultsTable.foodCategoryId}, {id})
                        .select {
                        (ResultsTable.sellerId eq sellerId)
                    }
                        .orderBy(ResultsTable.dateCreated to SortOrder.DESC)
                        .map { rowToResultResponse(it) }
                }
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

    private fun rowToResultCategory(row: ResultRow?): ResultCategory? {
        if (row == null) return null

        return ResultCategory(
            id = row[ResultCategoryTable.id],
            title = row[ResultCategoryTable.title],
            image_path = row[ResultCategoryTable.imagePath]!!,
            seller_category_id = row[ResultCategoryTable.sellerCategoryId]
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
}