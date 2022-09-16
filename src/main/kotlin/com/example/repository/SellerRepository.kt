package com.example.repository

import com.example.models.Seller
import com.example.models.responses.SellerDetailsResponse
import com.example.models.responses.SellerListResponse
import com.example.models.responses.SellerResponse
import com.example.utils.ServiceResult

interface SellerRepository {

    suspend fun insertSeller(seller: Seller): ServiceResult<Seller>

    suspend fun getSellers(offset: Long = 0L): ServiceResult<SellerListResponse?>
    suspend fun getSellerDetails(sellerId: Long): ServiceResult<SellerDetailsResponse>
    suspend fun getSellersByTitle(sellerTitle: String?, offset: Long = 0L): ServiceResult<SellerListResponse?> // get similar sellers by part of string passed
    suspend fun getSellersByDescription(description: String?, offset: Long = 0L): ServiceResult<SellerListResponse?>
    suspend fun getSellersByStateId(stateId: Int, offset: Long = 0L): ServiceResult<SellerListResponse?>
    suspend fun getSellersByCityId(cityId: Int, offset: Long = 0L): ServiceResult<SellerListResponse?>
    suspend fun getSellersByLocationId(locationId: Long, offset: Long = 0L): ServiceResult<SellerListResponse?>
    suspend fun getSellersByLocationTitle(locationTitle: String?, offset: Long = 0L): ServiceResult<SellerListResponse?>
    suspend fun getSellerByResultId(resultId: Long): ServiceResult<SellerResponse?>
    suspend fun getSellersBySellerCategoryId(sellerCategoryId: Int, offset: Long = 0L): ServiceResult<SellerListResponse?>
    suspend fun getSellersByResultCategoryId(resultCategoryId: Int, offset: Long = 0L): ServiceResult<SellerListResponse?>
    suspend fun getSellersByFoodCategoryId(foodCategoryId: Int, offset: Long = 0L): ServiceResult<SellerListResponse?>
    suspend fun getSellersByDeliveryDuration(minutes: Int, offset: Long = 0L): ServiceResult<SellerListResponse?>
    suspend fun getSellersByDeliveryFee(fee: Long, offset: Long = 0L): ServiceResult<SellerListResponse?>

    suspend fun updateSeller(sellerId: Long, seller: Seller): ServiceResult<Seller>

    suspend fun deleteSellerById(sellerId: Long, offset: Long = 0L): ServiceResult<SellerListResponse?>
    suspend fun deleteSellers(offset: Long = 0L): ServiceResult<SellerListResponse?>
}