package com.example.routings

import com.example.repository.CityRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.cityRouting(cityRepository: CityRepository) {
    routing {
        route("/city") {

            get("/all") {
                val cities = cityRepository.getCities()
                call.respond(cities)
            }
            get("/[id]") {
                val cityId = call.parameters["id"]?.toInt()
                val city = cityRepository.getCityById(cityId!!)
                call.respond(city!!)
            }
            get("/[title]") {
                val cityTitle = call.parameters["title"] // check if condition for null error
                val city = cityRepository.getCityByTitle(cityTitle!!)
                call.respond(city!!)
            }
        }
    }
}