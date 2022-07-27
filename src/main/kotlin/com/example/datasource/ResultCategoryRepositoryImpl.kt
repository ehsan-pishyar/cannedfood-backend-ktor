package com.example.datasource

import com.example.models.ResultCategory
import com.example.repository.ResultCategoryRepository

class ResultCategoryRepositoryImpl : ResultCategoryRepository {

    override suspend fun insertCategory(resultCategory: ResultCategory): ResultCategory? {
        TODO("Not yet implemented")
    }

    override suspend fun getCategories(): List<ResultCategory> {
        TODO("Not yet implemented")
    }

    override suspend fun getCategoryById(categoryId: Int): ResultCategory {
        TODO("Not yet implemented")
    }

    override suspend fun getCategoryByTitle(categoryTitle: String): ResultCategory {
        TODO("Not yet implemented")
    }

    override suspend fun updateCategoryById(categoryId: Int): ResultCategory {
        TODO("Not yet implemented")
    }

    override suspend fun updateCategoryByTitle(categoryTitle: String): ResultCategory {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCategoryById(categoryId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCategoryByTitle(categoryTitle: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCategories() {
        TODO("Not yet implemented")
    }
}