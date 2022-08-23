package com.example.models.responses

import kotlinx.serialization.Serializable

@Serializable
data class ResultsCategoryResponse(
    val id: Int?,
    val title: String?,
    val image_path: String?,
    val seller_category: String?
)

@Serializable
data class FoodCategoryResponse(
    val id: Int?,
    val title: String?,
    val image_path: String?,
    val results_category: String?
)