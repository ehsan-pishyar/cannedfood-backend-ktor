package com.example.datasource

import com.example.models.FoodCategory
import com.example.repository.FoodCategoryRepository

class FoodCategoryRepositoryImpl : FoodCategoryRepository {

    override suspend fun insertType(foodCategory: FoodCategory): FoodCategory? {
        TODO("Not yet implemented")
    }

    override suspend fun getTypes(): List<FoodCategory> {
        TODO("Not yet implemented")
    }

    override suspend fun getTypeById(typeId: Int): FoodCategory {
        TODO("Not yet implemented")
    }

    override suspend fun getTypeByTitle(typeTitle: String): FoodCategory? {
        TODO("Not yet implemented")
    }

    override suspend fun updateTypeById(typeId: Int): FoodCategory {
        TODO("Not yet implemented")
    }

    override suspend fun updateTypeByTitle(typeTitle: String): FoodCategory {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTypeById(typeId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTypeByTitle(typeTitle: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTypes() {
        TODO("Not yet implemented")
    }
}