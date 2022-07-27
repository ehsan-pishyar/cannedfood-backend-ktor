package com.example.repository

import com.example.models.SellerCategory

interface SellerCategoryRepository {

    suspend fun insertSellerCategory(sellerCategory: SellerCategory): SellerCategory?

    suspend fun getSellerCategories(): List<SellerCategory?>
    suspend fun getSellerCategoryById(id: Int): SellerCategory?
    suspend fun getSellerCategoryByTitle(title: String?): SellerCategory?

    suspend fun updateSellerCategoryById(id: Int): SellerCategory?
    suspend fun updateSellerCategoryByTitle(title: String?): SellerCategory?

    suspend fun deleteSellerCategoryById(id: Int)
    suspend fun deleteSellerCategoryByTitle(title: String?)
    suspend fun deleteSellerCategories()
}