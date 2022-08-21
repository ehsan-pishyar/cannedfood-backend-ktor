package com.example.datasource

import com.example.models.Results
import com.example.models.responses.ResultResponse
import com.example.repository.ResultsRepository
import com.example.tables.DatabaseFactory.dbQuery
import com.example.tables.FoodCategoryTable
import com.example.tables.ResultRatingTable
import com.example.tables.ResultsTable
import com.example.tables.SellerTable
import com.example.utils.ErrorCode
import com.example.utils.ServiceResult
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*

class ResultsRepositoryImpl : ResultsRepository {

    override suspend fun insertResult(result: Results): ServiceResult<Results> {
        return try {
            dbQuery {
                ResultsTable.insert {
                    it[sellerId] = result.seller_id
                    it[title] = result.title
                    it[description] = result.description
                    it[foodCategoryId] = result.food_category_id
                    it[imagePath] = result.image_path
                    it[price] = result.price
                    it[discount] = result.discount
                    it[dateCreated] = result.date_created
                    it[prepareDuration] = result.prepare_duration
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

    override suspend fun getResults(): ServiceResult<List<ResultResponse?>> {
        return try {
            dbQuery {
                ResultsTable.innerJoin(SellerTable).innerJoin(FoodCategoryTable).innerJoin(ResultRatingTable)
                    .selectAll()
                    .orderBy(ResultsTable.id to SortOrder.ASC)
                    .limit(20)
                    .map {
                        rowToResultResponse(it)
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

    override suspend fun getResultById(resultId: Int): ServiceResult<ResultResponse?> {
        return try {
            dbQuery {
                ResultsTable.innerJoin(SellerTable).innerJoin(FoodCategoryTable).innerJoin(ResultRatingTable)
                    .select {
                        ResultsTable.id eq resultId
                    }.map {
                        rowToResultResponse(it)
                    }.singleOrNull()
            }?.let {
                ServiceResult.Success(it)
            } ?: ServiceResult.Error(ErrorCode.DATABASE_ERROR)
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getResultsByTitle(resultTitle: String): ServiceResult<List<ResultResponse?>> {
        TODO("Not yet implemented")
    }

    override suspend fun getResultsByDescription(description: String?): ServiceResult<List<ResultResponse?>> {
        TODO("Not yet implemented")
    }

    override suspend fun getResultsBySellerId(sellerId: Int): ServiceResult<List<ResultResponse?>> {
        TODO("Not yet implemented")
    }

    override suspend fun getResultsBySellerCategoryId(sellerCategoryId: Int): ServiceResult<List<ResultResponse?>> {
        TODO("Not yet implemented")
    }

    override suspend fun getResultsByResultCategoryId(resultCategoryId: Int): ServiceResult<List<ResultResponse?>> {
        TODO("Not yet implemented")
    }

    override suspend fun getResultsByFoodCategoryId(foodCategoryId: Int): ServiceResult<List<ResultResponse?>> {
        TODO("Not yet implemented")
    }

    override suspend fun getResultsByPrice(price: Int): ServiceResult<List<ResultResponse?>> {
        TODO("Not yet implemented")
    }

    override suspend fun getResultsByRating(rating: Double): ServiceResult<List<ResultResponse?>> {
        TODO("Not yet implemented")
    }

    override suspend fun getResultsByVoteCount(voteCount: Int): ServiceResult<List<ResultResponse?>> {
        TODO("Not yet implemented")
    }

    override suspend fun getResultsByDiscount(discount: Int): ServiceResult<List<ResultResponse?>> {
        TODO("Not yet implemented")
    }

    override suspend fun getResultsByDateAdded(dateAdded: String): ServiceResult<List<ResultResponse?>> {
        TODO("Not yet implemented")
    }

    override suspend fun getResultsByPrepareDuration(minutes: Int): ServiceResult<List<ResultResponse?>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateResult(results: Results): ServiceResult<ResultResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteResultById(resultId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteResults() {
        TODO("Not yet implemented")
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
            prepare_duration = row[ResultsTable.prepareDuration]!!,
        )
    }

    private fun rowToResultResponse(row: ResultRow?): ResultResponse? {
        if (row == null) return null

        return ResultResponse(
            id = row[ResultsTable.id],
            seller_title = row[SellerTable.title],
            title = row[ResultsTable.title],
            description = row[ResultsTable.description]!!,
            food_category = row[FoodCategoryTable.title],
            image_path = row[ResultsTable.imagePath],
            price = row[ResultsTable.price],
            discount = row[ResultsTable.discount]!!,
            rating = row[ResultRatingTable.rating],
            vote_count = row[ResultRatingTable.fromCustomerId.count()],
            date_created = row[ResultsTable.dateCreated],
            prepare_duration = row[ResultsTable.prepareDuration]!!,
        )
    }
}