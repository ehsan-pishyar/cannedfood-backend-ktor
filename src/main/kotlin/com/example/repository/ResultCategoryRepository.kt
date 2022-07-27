package com.example.repository

import com.example.models.ResultCategory

interface ResultCategoryRepository {

    suspend fun insertCategory(resultCategory: ResultCategory): ResultCategory?

    suspend fun getCategories(): List<ResultCategory?>
    suspend fun getCategoryById(categoryId: Int): ResultCategory?
    suspend fun getCategoryByTitle(categoryTitle: String): ResultCategory?

    suspend fun updateCategoryById(categoryId: Int): ResultCategory
    suspend fun updateCategoryByTitle(categoryTitle: String): ResultCategory

    suspend fun deleteCategoryById(categoryId: Int)
    suspend fun deleteCategoryByTitle(categoryTitle: String)
    suspend fun deleteCategories()
}