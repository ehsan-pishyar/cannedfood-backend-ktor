package com.example.repository

import com.example.models.SellerCategory

class SellerCategoryRepositoryImpl : SellerCategoryRepository {

    override suspend fun insertSellerCategory(sellerCategory: SellerCategory): SellerCategory? {
        TODO("Not yet implemented")
    }

    override suspend fun getSellerCategories(): List<SellerCategory?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellerCategoryById(id: Int): SellerCategory? {
        TODO("Not yet implemented")
    }

    override suspend fun getSellerCategoryByTitle(title: String?): SellerCategory? {
        TODO("Not yet implemented")
    }

    override suspend fun updateSellerCategoryById(id: Int): SellerCategory? {
        TODO("Not yet implemented")
    }

    override suspend fun updateSellerCategoryByTitle(title: String?): SellerCategory? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSellerCategoryById(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSellerCategoryByTitle(title: String?) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSellerCategories() {
        TODO("Not yet implemented")
    }
}