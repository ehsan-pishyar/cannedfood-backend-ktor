package com.example.repository

import com.example.models.Results

interface ResultsRepository {

    suspend fun insertResult(result: Results)

    suspend fun getResults(): List<Results?>
    suspend fun getResultById(resultId: Int): Results?
    suspend fun getResultsByTitle(resultTitle: String): List<Results?>
    suspend fun getResultsByDescription(description: String?): List<Results?>
    suspend fun getResultsBySellerId(sellerId: Int): List<Results?>
    suspend fun getResultsBySellerCategoryId(sellerCategoryId: Int): List<Results?>
    suspend fun getResultsByResultCategoryId(resultCategoryId: Int): List<Results?>
    suspend fun getResultsByFoodCategoryId(foodCategoryId: Int): List<Results?>
    suspend fun getResultsByPrice(price: Int): List<Results?>
    suspend fun getResultsByRating(rating: Double): List<Results?>
    suspend fun getResultsByVoteCount(voteCount: Int): List<Results?>
    suspend fun getResultsByDiscount(discount: Int): List<Results?>
    suspend fun getResultsByDateAdded(dateAdded: String): List<Results?>
    suspend fun getResultsByPrepareDuration(minutes: Int): List<Results?>

    suspend fun updateResult(resultId: Int, results: Results)

    suspend fun deleteResultById(resultId: Int)
    suspend fun deleteResults()
}