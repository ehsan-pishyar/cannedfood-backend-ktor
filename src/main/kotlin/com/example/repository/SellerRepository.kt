package com.example.repository

import com.example.models.Seller
import com.example.models.responses.SellerResponse
import com.example.utils.ServiceResult

interface SellerRepository {

    suspend fun insertSeller(seller: Seller): ServiceResult<Seller>

    suspend fun getSellers(): ServiceResult<List<SellerResponse?>>
    suspend fun getSellerById(sellerId: Long): ServiceResult<SellerResponse>
    suspend fun getSellersByTitle(sellerTitle: String?): ServiceResult<List<SellerResponse?>> // get similar sellers by part of string passed
    suspend fun getSellersByDescription(description: String?): ServiceResult<List<SellerResponse?>>
    suspend fun getSellersByStateId(stateId: Int): ServiceResult<List<SellerResponse?>>
    suspend fun getSellersByCityId(cityId: Int): ServiceResult<List<SellerResponse?>>
    suspend fun getSellersByLocationId(locationId: Long): ServiceResult<List<SellerResponse?>>
    suspend fun getSellersByLocationTitle(locationTitle: String?): ServiceResult<List<SellerResponse?>>
    suspend fun getSellersByResultId(resultId: Long): ServiceResult<SellerResponse?>
    suspend fun getSellersByResultTitle(resultTitle: String?): ServiceResult<List<SellerResponse?>>
    suspend fun getSellersBySellerCategoryId(sellerCategoryId: Int): ServiceResult<List<SellerResponse?>>
    suspend fun getSellersByResultCategoryId(resultCategoryId: Int): ServiceResult<List<SellerResponse?>>
    suspend fun getSellersByFoodCategoryId(foodCategoryId: Int): ServiceResult<List<SellerResponse?>>
    suspend fun getSellersByOpenStatus(isOpen: Boolean): ServiceResult<List<SellerResponse?>>
    suspend fun getSellersByDeliveryDuration(minutes: Int): ServiceResult<List<SellerResponse?>>
    suspend fun getSellersByDeliveryFee(fee: Long): ServiceResult<List<SellerResponse?>>

    suspend fun updateSeller(sellerId: Long, seller: Seller): ServiceResult<SellerResponse>

    suspend fun deleteSeller(sellerId: Long)
    suspend fun deleteSellers()
}