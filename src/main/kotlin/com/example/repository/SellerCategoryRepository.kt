package com.example.repository

import com.example.models.SellerCategory
import com.example.utils.ServiceResult

interface SellerCategoryRepository {

    suspend fun getSellersCategories(): ServiceResult<List<SellerCategory?>>// list of seller categories for home fragment
    suspend fun getSellersCategoryById(id: Int): ServiceResult<SellerCategory>
    suspend fun getSellerCategoriesByTitle(title: String?): ServiceResult<List<SellerCategory?>> // for search result

    suspend fun deleteSellerCategory(id: Int): ServiceResult<List<SellerCategory?>>
    suspend fun deleteSellerCategories(): ServiceResult<List<SellerCategory?>>
}