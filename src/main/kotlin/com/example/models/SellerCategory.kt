package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class SellerCategory(
    val id: Int,
    val title: String,
    val image_path: String
)
