package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class State(
    val id: Int = 0,
    val title: String = ""
)

@Serializable
data class City(
    val id: Int = 0,
    val title: String = "",
    val state_id: Int = 0
)

@Serializable
data class Location(
    val id: Long = 0,
    val title: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val city_id: Int = 0
)
