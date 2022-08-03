package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class FoodCategory(
    val id: Int,
    val title: String,
    val seller_category_id: Int,
    val result_category_id: Int
)