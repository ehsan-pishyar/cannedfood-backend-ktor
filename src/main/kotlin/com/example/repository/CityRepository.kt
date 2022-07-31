package com.example.repository

import com.example.models.City

interface CityRepository {

    suspend fun insertCity(stateId: Int, city: City): City?

    suspend fun getCities(stateId: Int): List<City?>
    suspend fun getCityByTitle(stateId: Int, cityTitle: String): City?

    suspend fun updateCityById(stateId: Int, cityId: Int, city: City): City
    suspend fun updateCityByTitle(stateId: Int, cityTitle: String, city: City): City

    suspend fun deleteCityById(stateId: Int, cityId: Int)
    suspend fun deleteCityByTitle(stateId: Int, cityTitle: String)
    suspend fun deleteCities()
}