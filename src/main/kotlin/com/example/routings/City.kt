package com.example.routings

import com.example.repository.CityRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.cityRouting(cityRepository: CityRepository) {
    routing {

        route("/cities") {

//            get("/") {
//                val stateId = call.request.queryParameters["state_id"]?.toInt() // cities/?state=21
//                val cities = cityRepository.getCities(stateId!!)
//                call.respond(cities)
//            }
//            get("/city/") {
//                val params = call.request.rawQueryParameters
//                val stateId = params["state_id"]?.toInt()
//                val cityId = params["city_id"]?.toInt()
//                val city = cityRepository.getCityById(stateId!!, cityId!!)
//                call.respond(city!!)
//            }
            get("/") {
                val params = call.request.rawQueryParameters
                val stateId = params["state_id"]?.toInt()
                val cityTitle = params["city_title"]

                if (cityTitle == null) {
                    val cities = cityRepository.getCities(stateId!!)
                    call.respond(cities)
                } else {
                    val city = cityRepository.getCityByTitle(stateId!!, cityTitle)
                    call.respond(city!!)
                }
            }
        }
    }
}