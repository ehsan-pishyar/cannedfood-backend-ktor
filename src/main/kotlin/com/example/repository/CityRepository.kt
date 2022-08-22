package com.example.repository

import com.example.models.City
import com.example.models.responses.CityResponse
import com.example.utils.ServiceResult

interface CityRepository {

    suspend fun insertCity(city: City): ServiceResult<City?>

    suspend fun getCities(stateId: Int): ServiceResult<List<City?>>
    suspend fun getCityById(stateId:Int, cityId: Int): ServiceResult<City?>
    suspend fun getCitiesByTitle(stateId: Int, cityTitle: String?): ServiceResult<List<City?>>

    suspend fun updateCity(cityId: Int, city: City): ServiceResult<City>

    suspend fun deleteCity(cityId: Int)
    suspend fun deleteCitiesOfState(stateId: Int)
    suspend fun deleteCities()
}