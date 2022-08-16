package com.example.repository

import com.example.models.Location

interface LocationRepository {

    suspend fun insertLocation(location: Location)

    suspend fun getLocations(cityId: Int): List<Location?>
    suspend fun getLocation(locationId: Int): Location
    suspend fun getLocationsByTitle(title: String?): List<Location?>

    suspend fun updateLocation(locationId: Int, location: Location)

    suspend fun deleteLocation(locationId: Int)
    suspend fun deleteLocations()
}