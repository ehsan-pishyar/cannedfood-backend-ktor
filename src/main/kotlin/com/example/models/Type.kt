package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Type(
    val id: Int,
    val title: String
)