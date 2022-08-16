package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class ResultCategory(
    val id: Int,
    val title: String,
    val image_path: String,
    val seller_category_id: Int
)
