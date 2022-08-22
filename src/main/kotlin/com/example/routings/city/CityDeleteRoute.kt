package com.example.routings.city

import com.example.repository.CityRepository
import com.example.utils.Routes
import io.ktor.server.routing.*

fun Route.deleteCities(
    cityRepository: CityRepository
) {
    route(Routes.CITY_ROUTE) {
        delete("/") {

        }
    }
}