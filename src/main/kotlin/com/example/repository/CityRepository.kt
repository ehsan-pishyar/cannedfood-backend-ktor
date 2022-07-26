package com.example.repository

import com.example.models.City

interface CityRepository {

    suspend fun insertCity(city: City): City?

    suspend fun getCities(): List<City?>
    suspend fun getCityById(cityId: Int): City?
    suspend fun getCityByTitle(cityTitle: String): City?

    suspend fun updateCityById(cityId: Int): City
    suspend fun updateCityByTitle(cityTitle: String): City

    suspend fun deleteCityById(cityId: Int)
    suspend fun deleteCityByTitle(cityTitle: String)
    suspend fun deleteCities()
}