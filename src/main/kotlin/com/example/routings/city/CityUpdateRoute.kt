package com.example.routings.city

import com.example.models.City
import com.example.repository.CityRepository
import com.example.utils.Routes
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Route.updateCity(
    cityRepository: CityRepository
) {
    route(Routes.CITY_ROUTE) {
        put("/city") {

        }
    }
}