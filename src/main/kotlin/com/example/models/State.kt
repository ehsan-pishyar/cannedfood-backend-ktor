package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class State(
    val id: Int,
    val title: String
)
