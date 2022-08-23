package com.example.repository

import com.example.models.FoodCategory
import com.example.models.responses.FoodCategoryResponse
import com.example.utils.ServiceResult

interface FoodCategoryRepository {

    suspend fun insertFoodCategory(foodCategory: FoodCategory): ServiceResult<FoodCategory>

    suspend fun getFoodCategories(resultCategoryId: Int): ServiceResult<List<FoodCategoryResponse?>>

    suspend fun getFoodCategoryById(foodCategoryId: Int): ServiceResult<FoodCategoryResponse>
    suspend fun getFoodCategoriesByTitle(foodCategoryTitle: String?): ServiceResult<List<FoodCategoryResponse?>>

    suspend fun updateFoodCategory(foodCategoryId: Int, foodCategory: FoodCategory): ServiceResult<FoodCategory>

    suspend fun deleteFoodCategoryById(foodCategoryId: Int): ServiceResult<List<FoodCategoryResponse?>>
    suspend fun deleteFoodCategoriesOfResultCategory(resultCategoryId: Int): ServiceResult<List<FoodCategoryResponse?>>
    suspend fun deleteFoodCategories(): ServiceResult<List<FoodCategoryResponse?>>
}