package com.example.datasource

import com.example.models.City
import com.example.repository.CityRepository

class CityRepositoryImpl : CityRepository {

    override suspend fun insertCity(city: City): City? {
        TODO("Not yet implemented")
    }

    override suspend fun getCities(): List<City> {
        TODO("Not yet implemented")
    }

    override suspend fun getCityById(cityId: Int): City {
        TODO("Not yet implemented")
    }

    override suspend fun getCityByTitle(cityTitle: String): City? {
        TODO("Not yet implemented")
    }

    override suspend fun updateCityById(cityId: Int): City {
        TODO("Not yet implemented")
    }

    override suspend fun updateCityByTitle(cityTitle: String): City {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCityById(cityId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCityByTitle(cityTitle: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCities() {
        TODO("Not yet implemented")
    }
}