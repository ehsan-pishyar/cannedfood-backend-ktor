package com.example.repository

import com.example.models.Results

interface ResultsRepository {

    suspend fun insertResult(result: Results?)

    suspend fun getResults(): List<Results?>
    suspend fun getResultById(resultId: Int): Results?
    suspend fun getResultsByTitle(resultTitle: String): List<Results?>
    suspend fun getResultsByDescription(description: String): List<Results?>
    suspend fun getResultsByTypeId(typeId: Int): List<Results?>
    suspend fun getResultsByTypeTitle(typeTitle: String): List<Results?>
    suspend fun getResultsByCategoryId(categoryId: Int): List<Results?>
    suspend fun getResultsByCategoryTitle(categoryTitle: String): List<Results?>
    suspend fun getResultsByPrice(price: Int): List<Results?>
    suspend fun getResultsByRating(rating: Double): List<Results?>
    suspend fun getResultsByVoteCount(totalVotes: Int): List<Results?>
    suspend fun getResultsByDiscount(discount: Int): List<Results?>
    suspend fun getResultsByDateAdded(date: String): List<Results?>
    suspend fun getResultsByPrepareDuration(minutes: Int): List<Results?>

    suspend fun updateResultById(resultId: Results): Results
    suspend fun updateResult(result: Results): Results

    suspend fun deleteResultById(resultId: Int)
    suspend fun deleteResultByTitle(resultTitle: String)
    suspend fun deleteResultsByDescription(description: String)
    suspend fun deleteResults()
}