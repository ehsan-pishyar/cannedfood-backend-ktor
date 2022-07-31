package com.example.repository

import com.example.models.FoodCategory

interface FoodCategoryRepository {

    suspend fun insertType(foodCategory: FoodCategory): FoodCategory?

    suspend fun getTypes(): List<FoodCategory?>
    suspend fun getTypeById(typeId: Int): FoodCategory?
    suspend fun getTypeByTitle(typeTitle: String): FoodCategory?

    suspend fun updateTypeById(typeId: Int): FoodCategory
    suspend fun updateTypeByTitle(typeTitle: String): FoodCategory

    suspend fun deleteTypeById(typeId: Int)
    suspend fun deleteTypeByTitle(typeTitle: String)
    suspend fun deleteTypes()
}