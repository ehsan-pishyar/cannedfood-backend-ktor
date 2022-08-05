package com.example.repository

import com.example.models.FoodCategory

interface FoodCategoryRepository {

    suspend fun insertFoodCategory(
        foodCategory: FoodCategory
    )

    suspend fun getFoodCategories(
        sellerCategoryId: Int,
        resultCategoryId: Int,
    ): List<FoodCategory?>
    suspend fun getFoodCategoriesByTitle(
        sellerCategoryId: Int,
        resultCategoryId: Int,
        foodCategoryTitle: String?
    ): List<FoodCategory?>
    suspend fun getFoodCategoriesByResultCategoryId(
        sellerCategoryId: Int,
        resultCategoryId: Int
    ): List<FoodCategory?>

    suspend fun updateFoodCategory(
        sellerCategoryId: Int,
        resultCategoryId: Int,
        foodCategoryId: Int,
        foodCategory: FoodCategory
    )

    suspend fun deleteFoodCategory(
        sellerCategoryId: Int,
        resultCategoryId: Int,
        foodCategoryId: Int
    )
    suspend fun deleteFoodCategoriesOfResultCategory(
        sellerCategoryId: Int,
        resultCategoryId: Int
    )
    suspend fun deleteFoodCategories()
}