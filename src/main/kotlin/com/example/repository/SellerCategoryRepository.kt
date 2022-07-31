package com.example.repository

import com.example.models.SellerCategory

interface SellerCategoryRepository {

    suspend fun getSellersCategories(): List<SellerCategory?> // list of seller categories for home fragment
    suspend fun getSellerCategoryByTitle(sellerCategoryTitle: String?): List<SellerCategory?> // for search result

    suspend fun deleteSellerCategoryById(sellerCategoryId: Int)
    suspend fun deleteSellerCategories()
}