package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Results(
    val result_id: Int,
    val seller_id: Int,
    val title: String,
    val description: String,
    val seller_category_id: Int,
    val result_category_id: Int,
    val food_category_id: Int?,
    val image_path: String,
    val price: Int,
    val rating: Double?,
    val vote_count: Int,
    val discount: Int?,
    val date_added: String,
    val prepare_duration: Int
)
