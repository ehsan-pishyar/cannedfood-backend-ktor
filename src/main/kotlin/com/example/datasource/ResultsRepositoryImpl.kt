package com.example.datasource

import com.example.models.Results
import com.example.repository.ResultsRepository

class ResultsRepositoryImpl : ResultsRepository {

    override suspend fun insertResult(result: Results): Results? {
        TODO("Not yet implemented")
    }

    override suspend fun getResults(): List<Results> {
        TODO("Not yet implemented")
    }

    override suspend fun getResultById(resultId: Int): Results {
        TODO("Not yet implemented")
    }

    override suspend fun getResultsByTitle(resultTitle: String): List<Results> {
        TODO("Not yet implemented")
    }

    override suspend fun getResultsByDescription(description: String): List<Results> {
        TODO("Not yet implemented")
    }

    override suspend fun getResultsByTypeId(typeId: Int): List<Results> {
        TODO("Not yet implemented")
    }

    override suspend fun getResultsByTypeTitle(typeTitle: String): List<Results?> {
        TODO("Not yet implemented")
    }

    override suspend fun getResultsByCategoryId(categoryId: Int): List<Results> {
        TODO("Not yet implemented")
    }

    override suspend fun getResultsByCategoryTitle(categoryTitle: String): List<Results?> {
        TODO("Not yet implemented")
    }

    override suspend fun getResultsByPrice(price: Int): List<Results> {
        TODO("Not yet implemented")
    }

    override suspend fun getResultsByRating(rating: Double): List<Results> {
        TODO("Not yet implemented")
    }

    override suspend fun getResultsByVoteCount(totalVotes: Int): List<Results> {
        TODO("Not yet implemented")
    }

    override suspend fun getResultsByDiscount(discount: Int): List<Results> {
        TODO("Not yet implemented")
    }

    override suspend fun getResultsByDateAdded(date: String): List<Results> {
        TODO("Not yet implemented")
    }

    override suspend fun getResultsByPrepareDuration(minutes: Int): List<Results> {
        TODO("Not yet implemented")
    }

    override suspend fun updateResultById(resultId: Results): Results {
        TODO("Not yet implemented")
    }

    override suspend fun updateResult(result: Results): Results {
        TODO("Not yet implemented")
    }

    override suspend fun deleteResultById(resultId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteResultByTitle(resultTitle: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteResultsByDescription(description: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteResults() {
        TODO("Not yet implemented")
    }
}