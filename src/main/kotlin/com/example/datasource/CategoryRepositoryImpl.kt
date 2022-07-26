package com.example.datasource

import com.example.models.Category
import com.example.repository.CategoryRepository

class CategoryRepositoryImpl : CategoryRepository {

    override suspend fun insertCategory(category: Category): Category? {
        TODO("Not yet implemented")
    }

    override suspend fun getCategories(): List<Category> {
        TODO("Not yet implemented")
    }

    override suspend fun getCategoryById(categoryId: Int): Category {
        TODO("Not yet implemented")
    }

    override suspend fun getCategoryByTitle(categoryTitle: String): Category {
        TODO("Not yet implemented")
    }

    override suspend fun updateCategoryById(categoryId: Int): Category {
        TODO("Not yet implemented")
    }

    override suspend fun updateCategoryByTitle(categoryTitle: String): Category {
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