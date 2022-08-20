package com.example.models.responses

import com.example.models.City
import com.example.models.ResponseErrors
import com.example.models.State
import kotlinx.serialization.Serializable

@Serializable
data class LocationResponse(
    val location_title: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val city_id: Int = 0,
    val city_title: String = "",
    val state_id: Int = 0,
    val state_title: String = ""
)

@Serializable
data class CityResponse(
    val city_id: Int = 0,
    val city_title: String = "",
    val state: State? = null
)

@Serializable
data class StateResponse(
    val state_id: Int = 0,
    val state_title: String = "",
    val cities: City?
)
