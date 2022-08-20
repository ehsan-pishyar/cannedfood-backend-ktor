package com.example.repository

import com.example.models.Location
import com.example.models.responses.LocationResponse
import com.example.utils.ServiceResult

interface LocationRepository {

    suspend fun insertLocation(location: Location): ServiceResult<Location?>

    suspend fun getLocations(cityId: Int): ServiceResult<List<LocationResponse?>>
    suspend fun getLocation(locationId: Int): ServiceResult<LocationResponse?>
    suspend fun getLocationsByTitle(title: String?): ServiceResult<List<LocationResponse?>>

    suspend fun updateLocation(location: Location)

    suspend fun deleteLocation(locationId: Int)
    suspend fun deleteLocations()
}