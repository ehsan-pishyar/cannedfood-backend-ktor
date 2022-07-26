package com.example.datasource

import com.example.models.Seller
import com.example.repository.SellerRepository

class SellerRepositoryImpl : SellerRepository {

    override suspend fun insertSeller(seller: Seller?) {
        TODO("Not yet implemented")
    }

    override suspend fun getSellers(): List<Seller> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellerById(sellerId: Int): Seller {
        TODO("Not yet implemented")
    }

    override suspend fun getSellerByTitle(sellerTitle: String): Seller? {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByTitleSlice(sellerTitleSlice: String): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByDescription(description: String): List<Seller> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByStateId(stateId: Int): List<Seller> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByStateTitle(stateTitle: String): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByCityId(cityId: Int): List<Seller> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByCityTitle(cityTitle: String): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByLocationTitle(locationTitle: String): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByResultsId(resultId: Int): List<Seller> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByResultsTitle(resultTitle: String): List<Seller?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByOpenStatus(isOpen: Boolean): List<Seller> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByDeliveryDuration(minutes: Int): List<Seller> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellersByDeliveryFee(fee: Int): List<Seller> {
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