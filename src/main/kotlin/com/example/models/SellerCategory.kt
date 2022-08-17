package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class SellerCategory(
    val sc_id: Int = 0,
    val title: String = "",
    val image_path: String = ""
)

@Serializable
data class ResultCategory(
    val rc_id: Int = 0,
    val title: String = "",
    val image_path: String = "",
    val seller_category_id: Int = 0
)

@Serializable
data class FoodCategory(
    val fc_id: Int = 0,
    val title: String = "",
    val image_path: String = "",
    val result_category_id: Int = 0
)
