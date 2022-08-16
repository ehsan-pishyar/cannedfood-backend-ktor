package com.example.repository

import com.example.models.ResultCategory

interface ResultCategoryRepository {

    suspend fun insertResultCategory(
        resultCategory: ResultCategory
    )

    suspend fun getResultCategories(
        sellerCategoryId: Int
    ): List<ResultCategory?>
    suspend fun getResultCategoriesByTitle(
        categoryTitle: String?
    ): List<ResultCategory?> // for search result

    suspend fun updateResultCategory(
        resultCategoryId: Int,
        resultCategory: ResultCategory
    )

    suspend fun deleteResultCategory(
        resultCategoryId: Int
    )
    suspend fun deleteResultCategoriesOfSellerCategory(
        sellerCategoryId: Int
    )
    suspend fun deleteResultCategories()
}