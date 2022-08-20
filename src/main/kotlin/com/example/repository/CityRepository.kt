package com.example.repository

import com.example.models.City
import com.example.models.responses.CityResponse
import com.example.utils.ServiceResult

interface CityRepository {

    suspend fun insertCity(city: City): ServiceResult<CityResponse?>

    suspend fun getCities(stateId: Int): ServiceResult<List<CityResponse?>>
    suspend fun getCityById(stateId:Int, cityId: Int): ServiceResult<CityResponse?>
    suspend fun getCitiesByTitle(stateId: Int, cityTitle: String?): ServiceResult<List<CityResponse?>>

    suspend fun updateCity(city: City)

    suspend fun deleteCity(cityId: Int)
    suspend fun deleteCitiesOfState(stateId: Int)
    suspend fun deleteCities()
}