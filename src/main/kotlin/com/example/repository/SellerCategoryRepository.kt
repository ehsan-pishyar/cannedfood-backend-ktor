package com.example.repository

import com.example.models.SellerCategory

interface SellerCategoryRepository {

    suspend fun getSellersCategories(): List<SellerCategory?> // list of seller categories for home fragment
    suspend fun getSellerCategoriesByTitle(sellerCategoryTitle: String?): List<SellerCategory?> // for search result

    suspend fun deleteSellerCategory(sellerCategoryId: Int)
    suspend fun deleteSellerCategories()
}