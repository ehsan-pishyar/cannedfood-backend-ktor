package com.example.repository

import com.example.models.ResultCategory

interface ResultCategoryRepository {

    suspend fun insertResultCategory(
        sellerCategoryId: Int,
        resultCategory: ResultCategory
    ): ResultCategory?

    suspend fun getResultCategories(
        sellerCategoryId: Int
    ): List<ResultCategory?>
    suspend fun getResultCategoriesByTitle(
        sellerCategoryId: Int,
        categoryTitle: String?
    ): List<ResultCategory?> // for search result
    suspend fun getResultCategoriesBySellerCategoryId(
        sellerCategoryId: Int
    ): List<ResultCategory?> // for search result

    suspend fun updateResultCategory(
        sellerCategoryId: Int,
        resultCategoryId: Int,
        resultCategory: ResultCategory
    ): ResultCategory

    suspend fun deleteResultCategory(
        sellerCategoryId: Int,
        resultCategoryId: Int
    )
    suspend fun deleteCategories()
}