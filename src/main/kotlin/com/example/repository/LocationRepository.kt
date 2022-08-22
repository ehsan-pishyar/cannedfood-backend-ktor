package com.example.repository

import com.example.models.Location
import com.example.models.responses.LocationResponse
import com.example.utils.ServiceResult

interface LocationRepository {

    suspend fun insertLocation(location: Location): ServiceResult<Location?>

    suspend fun getLocations(cityId: Int): ServiceResult<List<LocationResponse?>>
    suspend fun getLocationById(locationId: Long): ServiceResult<LocationResponse?>
    suspend fun getLocationsByTitle(locationTitle: String?): ServiceResult<List<LocationResponse?>>

    suspend fun updateLocation(locationId: Long, location: Location): ServiceResult<Location>

    suspend fun deleteLocation(locationId: Long)
    suspend fun deleteLocations()
}