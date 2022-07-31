package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class City(
    val id: Int,
    val title: String,
    val state_id: Int
)
