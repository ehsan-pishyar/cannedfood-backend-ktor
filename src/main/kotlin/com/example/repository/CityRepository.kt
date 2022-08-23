package com.example.repository

import com.example.models.City
import com.example.models.responses.CityResponse
import com.example.utils.ServiceResult

interface CityRepository {

    suspend fun insertCity(city: City): ServiceResult<City?>

    suspend fun getCities(stateId: Int): ServiceResult<List<CityResponse?>>
    suspend fun getCityById(cityId: Int): ServiceResult<CityResponse?>
    suspend fun getCitiesByTitle(cityTitle: String?): ServiceResult<List<CityResponse?>>

    suspend fun updateCity(cityId: Int, city: City): ServiceResult<City?>

    suspend fun deleteCityById(cityId: Int): ServiceResult<List<CityResponse?>>
    suspend fun deleteCitiesOfState(stateId: Int): ServiceResult<List<CityResponse?>>
    suspend fun deleteCities(): ServiceResult<List<CityResponse?>>
}