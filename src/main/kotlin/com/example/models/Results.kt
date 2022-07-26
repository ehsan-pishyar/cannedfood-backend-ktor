package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Results(
    val id: Int,
    val title: String,
    val description: String,
    val type_id: Int,
    val category_id: Int,
    val image_path: String,
    val price: Int,
    val rating: Double,
    val vote_count: Int,
    val discount: Int,
    val date_added: String,
    val prepare_duration: Int
)
