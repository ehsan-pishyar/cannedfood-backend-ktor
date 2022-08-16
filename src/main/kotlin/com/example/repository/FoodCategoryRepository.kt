package com.example.repository

import com.example.models.FoodCategory

interface FoodCategoryRepository {

    suspend fun insertFoodCategory(
        foodCategory: FoodCategory
    )

    suspend fun getFoodCategories(
        resultCategoryId: Int,
    ): List<FoodCategory?>
    suspend fun getFoodCategoriesByTitle(
        foodCategoryTitle: String?
    ): List<FoodCategory?>

    suspend fun updateFoodCategory(
        foodCategoryId: Int,
        foodCategory: FoodCategory
    )
    suspend fun deleteFoodCategory(
        foodCategoryId: Int
    )
    suspend fun deleteFoodCategoriesOfResultCategory(
        resultCategoryId: Int
    )
    suspend fun deleteFoodCategories()
}