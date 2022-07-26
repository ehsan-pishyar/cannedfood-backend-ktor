package com.example.repository

import com.example.models.Seller

interface SellerRepository {

    suspend fun insertSeller(seller: Seller?)

    suspend fun getSellers(): List<Seller?>
    suspend fun getSellerById(sellerId: Int): Seller?
    suspend fun getSellerByTitle(sellerTitle: String): Seller?
    suspend fun getSellersByTitleSlice(sellerTitleSlice: String): List<Seller?> // get similar sellers by part of string passed
    suspend fun getSellersByDescription(description: String): List<Seller?>
    suspend fun getSellersByStateId(stateId: Int): List<Seller?>
    suspend fun getSellersByStateTitle(stateTitle: String): List<Seller?>
    suspend fun getSellersByCityId(cityId: Int): List<Seller?>
    suspend fun getSellersByCityTitle(cityTitle: String): List<Seller?>
    suspend fun getSellersByLocationTitle(locationTitle: String): List<Seller?>
    suspend fun getSellersByResultsId(resultId: Int): List<Seller?>
    suspend fun getSellersByResultsTitle(resultTitle: String): List<Seller?>
    suspend fun getSellersByOpenStatus(isOpen: Boolean): List<Seller?>
    suspend fun getSellersByDeliveryDuration(minutes: Int): List<Seller?>
    suspend fun getSellersByDeliveryFee(fee: Int): List<Seller?>

    suspend fun updateSellerById(sellerId: Int): Seller
    suspend fun updateSeller(seller: Seller): Seller

    suspend fun deleteSellerById(sellerId: Int)
    suspend fun deleteSellerByTitle(sellerTitle: String)
    suspend fun deleteSellersByRating(rating: Double)
    suspend fun deleteSellersByStateId(stateId: Int)
    suspend fun deleteSellersByStateTitle(stateTitle: String)
    suspend fun deleteSellersByCityId(cityId: Int)
    suspend fun deleteSellersByCityTitle(cityTitle: String)
    suspend fun deleteSellers()
}