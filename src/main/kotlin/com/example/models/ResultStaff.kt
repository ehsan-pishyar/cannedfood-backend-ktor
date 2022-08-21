package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Results(
    val id: Int = 0,
    val seller_id: Int = 0,
    val title: String = "",
    val description: String = "",
    val food_category_id: Int = 0,
    val image_path: String = "",
    val price: Long = 0L,
    val discount: Int = 0,
    val date_created: String = "",
    val prepare_duration: Int = 0
)

@Serializable
data class ResultRating(
    val id: Int = 0,
    val from_customer_id: Int = 0,
    val rating: Double = 0.0,
    val to_result_id: Int = 0
)

@Serializable
data class ResultComment(
    val id: Int = 0,
    val from_customer_id: Int = 0,
    val message: String = "",
    val to_result_id: Int = 0,
)
