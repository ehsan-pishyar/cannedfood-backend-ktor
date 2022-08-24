package com.example.repository

import com.example.models.Results
import com.example.models.responses.ResultResponse
import com.example.utils.ServiceResult

interface ResultsRepository {

    suspend fun insertResult(result: Results): ServiceResult<Results>

    suspend fun getResults(): ServiceResult<List<ResultResponse?>>
    suspend fun getResultById(resultId: Long): ServiceResult<ResultResponse?>
    suspend fun getResultsByTitle(resultTitle: String): ServiceResult<List<ResultResponse?>>
    suspend fun getResultsByDescription(description: String?): ServiceResult<List<ResultResponse?>>
    suspend fun getResultsBySellerId(sellerId: Long): ServiceResult<List<ResultResponse?>>
    suspend fun getResultsBySellerCategoryId(sellerCategoryId: Int): ServiceResult<List<ResultResponse?>>
    suspend fun getResultsByResultCategoryId(resultCategoryId: Int): ServiceResult<List<ResultResponse?>>
    suspend fun getResultsByFoodCategoryId(foodCategoryId: Int): ServiceResult<List<ResultResponse?>>
    suspend fun getResultsByPrice(price: Long): ServiceResult<List<ResultResponse?>>
    suspend fun getResultsByRating(rating: Double): ServiceResult<List<ResultResponse?>>
    suspend fun getResultsByVoteCount(voteCount: Long): ServiceResult<List<ResultResponse?>>
    suspend fun getResultsByDiscount(discount: Int): ServiceResult<List<ResultResponse?>>
    suspend fun getResultsByDateAdded(dateAdded: String): ServiceResult<List<ResultResponse?>>
    suspend fun getResultsByPrepareDuration(minutes: Int): ServiceResult<List<ResultResponse?>>

    suspend fun updateResult(results: Results): ServiceResult<ResultResponse>

    suspend fun deleteResultById(resultId: Long)
    suspend fun deleteResults()
}