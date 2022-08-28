package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Results(
    val id: Long = 0L,
    val seller_id: Long = 0L,
    val title: String = "",
    val description: String = "",
    val food_category_id: Int = 0,
    val image_path: String = "",
    val price: Long = 0L,
    val discount: Int = 0,
    val rating: Double = 0.0,
    val prepare_duration: Int = 0,
    val date_created: String = ""
)