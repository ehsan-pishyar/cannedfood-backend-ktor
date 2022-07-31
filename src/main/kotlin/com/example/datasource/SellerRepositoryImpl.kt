package com.example.datasource

import com.example.models.Seller
import com.example.repository.SellerRepository

class SellerRepositoryImpl : SellerRepository {

    override suspend fun insertSeller(seller: Seller): Seller? {
        TODO("Not yet implemented")
    }

    override suspend fun getSellers(): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByTitle(sellerTitle: String): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByDescription(description: String): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByStateId(stateId: Int): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByStateTitle(stateTitle: String): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByCityId(stateId: Int, cityId: Int): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByCityTitle(stateId: Int, cityTitle: String): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByLocationTitle(stateId: Int, cityId: Int, locationTitle: String): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByResultsId(resultId: Int): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByResultsTitle(resultTitle: String): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersBySellerCategoryId(sellerCategoryId: Int): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersBySellerCategoryTitle(sellerCategoryTitle: String?): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByResultCategoryId(sellerCategoryId: Int, resultCategoryId: Int): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByResultCategoryTitle(
        sellerCategoryId: Int,
        resultCategoryTitle: String?
    ): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByFoodCategoryId(
        sellerCategoryId: Int,
        resultCategoryId: Int,
        foodCategoryId: Int
    ): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByFoodCategoryTitle(
        sellerCategoryId: Int,
        resultCategoryId: Int,
        foodTypeCategoryTitle: String?
    ): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByOpenStatus(isOpen: Boolean): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByDeliveryDuration(minutes: Int): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByDeliveryFee(fee: Int): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun updateSellerById(sellerId: Int): Seller {
        TODO("Not yet implemented")
    }

    override suspend fun updateSeller(seller: Seller): Seller {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSellerById(sellerId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSellerByTitle(sellerTitle: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSellersByRating(rating: Double) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSellersByStateId(stateId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSellersByStateTitle(stateTitle: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSellersByCityId(cityId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSellersByCityTitle(cityTitle: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSellers() {
        TODO("Not yet implemented")
    }

}