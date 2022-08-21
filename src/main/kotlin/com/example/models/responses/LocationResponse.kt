package com.example.models.responses

import com.example.models.City
import com.example.models.ResponseErrors
import com.example.models.State
import kotlinx.serialization.Serializable

@Serializable
data class LocationResponse(
    val location_title: String?,
    val lat: Double?,
    val lon: Double?,
    val city: CityResponse? = null
)

@Serializable
data class CityResponse(
    val city_id: Int?,
    val city_title: String?,
    val state: State?
)

@Serializable
data class StateResponse(
    val state_id: Int?,
    val state_title: String?,
    val cities: City?
)