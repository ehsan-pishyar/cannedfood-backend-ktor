package com.example.models.responses

import com.example.models.City
import com.example.models.State
import kotlinx.serialization.Serializable

@Serializable
data class StateResponse(
    val id: Int?,
    val title: String?,
    val cities: City?
)

@Serializable
data class CityResponse(
    val id: Int?,
    val title: String?,
    val state: String?
)

@Serializable
data class LocationResponse(
    val id: Long?,
    val title: String?,
    val lat: Double?,
    val lon: Double?,
    val city: String?,
    val state: String?
)