package com.example.repository

import com.example.models.ResultRating
import com.example.models.SellerRating
import com.example.utils.ServiceResult

interface RatingRepository {

    suspend fun addRateToSeller(sellerId: Long, sellerRating: SellerRating): ServiceResult<SellerRating?>
    suspend fun addRateToResult(resultId: Long, resultRating: ResultRating): ServiceResult<ResultRating?>

    suspend fun getSellerRating(sellerId: Long): ServiceResult<Double>
    suspend fun getResultRating(resultId: Long): ServiceResult<Double>
}