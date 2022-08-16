package com.example.repository

import com.example.models.Seller

interface SellerRepository {

    suspend fun insertSeller(seller: Seller)

    suspend fun getSellers(): List<Seller?>
    suspend fun getSellersByTitle(
        sellerTitle: String?
    ): List<Seller?> // get similar sellers by part of string passed
    suspend fun getSellersByDescription(
        description: String?
    ): List<Seller?>
    suspend fun getSellersByStateId(
        stateId: Int
    ): List<Seller?>
    suspend fun getSellersByCityId(
        cityId: Int
    ): List<Seller?>
    suspend fun getSellersByLocationTitle(
        locationTitle: String?
    ): List<Seller?>
    suspend fun getSellersByResultsId(
        resultId: Int
    ): List<Seller?>
    suspend fun getSellersByResultsTitle(
        resultTitle: String?
    ): List<Seller?>
    suspend fun getSellersBySellerCategoryId(
        sellerCategoryId: Int
    ): List<Seller?>
    suspend fun getSellersByResultCategoryId(
        resultCategoryId: Int
    ): List<Seller?>
    suspend fun getSellersByFoodCategoryId(
        foodCategoryId: Int
    ): List<Seller?>
    suspend fun getSellersByOpenStatus(
        isOpen: Boolean
    ): List<Seller?>
    suspend fun getSellersByDeliveryDuration(
        minutes: Int
    ): List<Seller?>
    suspend fun getSellersByDeliveryFee(
        fee: Int
    ): List<Seller?>

    suspend fun updateSeller(
        sellerId: Int, seller: Seller
    )

    suspend fun deleteSellerById(
        sellerId: Int
    )
    suspend fun deleteSellersByRating(
        rating: Double
    )
    suspend fun deleteSellersByStateId(
        stateId: Int
    )
    suspend fun deleteSellersByCityId(
        cityId: Int
    )
    suspend fun deleteSellerByLocation(
        location: String?
    )
    suspend fun deleteSellers()
}