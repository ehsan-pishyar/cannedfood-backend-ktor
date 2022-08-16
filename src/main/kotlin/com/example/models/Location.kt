package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val id: Int,
    val title: String,
    val city_id: Int
)
