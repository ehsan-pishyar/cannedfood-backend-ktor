package com.example.repository

import com.example.models.Seller

interface SellerRepository {

    suspend fun insertSeller(seller: Seller): Seller?

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
    suspend fun getSellersByStateTitle(
        stateTitle: String?
    ): List<Seller?>
    suspend fun getSellersByCityId(
        stateId: Int, cityId: Int
    ): List<Seller?>
    suspend fun getSellersByCityTitle(
        stateId: Int, cityTitle: String?
    ): List<Seller?>
    suspend fun getSellersByLocationTitle(
        stateId: Int, cityId: Int, locationTitle: String?
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
    suspend fun getSellersBySellerCategoryTitle(
        sellerCategoryTitle: String?
    ): List<Seller?>
    suspend fun getSellersByResultCategoryId(
        sellerCategoryId: Int, resultCategoryId: Int
    ): List<Seller?>
    suspend fun getSellersByResultCategoryTitle(
        sellerCategoryId: Int, resultCategoryTitle: String?
    ): List<Seller?>
    suspend fun getSellersByFoodCategoryId(
        sellerCategoryId: Int,
        resultCategoryId: Int,
        foodCategoryId: Int
    ): List<Seller?>
    suspend fun getSellersByFoodCategoryTitle(
        sellerCategoryId: Int,
        resultCategoryId: Int,
        foodTypeCategoryTitle: String?
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
    ): Seller

    suspend fun deleteSellerById(
        sellerId: Int
    )
    suspend fun deleteSellersByRating(
        rating: Double
    )
    suspend fun deleteSellersByStateId(
        stateId: Int
    )
    suspend fun deleteSellersByStateTitle(
        stateTitle: String?
    )
    suspend fun deleteSellersByCityId(
        cityId: Int
    )
    suspend fun deleteSellersByCityTitle(
        cityTitle: String?
    )
    suspend fun deleteSellersByLocationTitle(
        locationTitle: String?
    )
    suspend fun deleteSellers()
}