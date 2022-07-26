package com.example.repository

import com.example.models.Category

interface CategoryRepository {

    suspend fun insertCategory(categoryTitle: String?)

    suspend fun getCategories(): List<Category?>
    suspend fun getCategoryById(categoryId: Int): Category?
    suspend fun getCategoryByTitle(categoryTitle: String): Category?

    suspend fun updateCategoryById(categoryId: Int): Category
    suspend fun updateCategoryByTitle(categoryTitle: String): Category

    suspend fun deleteCategoryById(categoryId: Int)
    suspend fun deleteCategoryByTitle(categoryTitle: String)
    suspend fun deleteCategories()
}