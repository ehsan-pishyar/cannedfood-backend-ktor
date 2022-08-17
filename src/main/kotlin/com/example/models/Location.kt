package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class State(
    val id: Int,
    val title: String
)

@Serializable
data class City(
    val id: Int,
    val title: String,
    val state_id: Int
)

@Serializable
data class Location(
    val id: Int,
    val title: String,
    val city_id: Int
)
