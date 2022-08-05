package com.example.repository

import com.example.models.City

interface CityRepository {

    suspend fun insertCity(city: City)

    suspend fun getCities(stateId: Int): List<City?>
    suspend fun getCitiesByTitle(stateId: Int, cityTitle: String?): List<City?>

    suspend fun updateCity(stateId: Int, cityId: Int, city: City)

    suspend fun deleteCity(stateId: Int, cityId: Int)
    suspend fun deleteCitiesOfState(stateId: Int)
    suspend fun deleteCities()
}