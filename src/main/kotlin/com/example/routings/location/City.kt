package com.example.routings.location

import com.example.models.City
import com.example.models.ResponseErrors
import com.example.models.responses.CityResponse
import com.example.repository.CityRepository
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.cityRouting(cityRepository: CityRepository) {
    routing {

        route("/cities") {

            post("/city") {
                val request = call.receiveOrNull<City>() ?: kotlin.run {
                    call.respond(HttpStatusCode.BadRequest)
                    return@post
                }

                when (val cityResponse = cityRepository.insertCity(request)) {
                    is ServiceResult.Success -> {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = cityResponse.data!!
                        )
                    }
                    is ServiceResult.Error -> {
                        call.respond(
                            status = HttpStatusCode.BadRequest,
                            message = cityResponse.errorCode.message
                        )
                    }
                }
            }

            get("/") {

                val params = call.request.rawQueryParameters
                val stateId = params["state_id"]?.toInt()
                val cityId = params["city_id"]?.toInt()
                val cityTitle = params["city_title"]

                if (stateId != null && cityId != null) {
                    when (val cityResponse = cityRepository.getCityById(stateId, cityId)) {
                        is ServiceResult.Success -> {
                            call.respond(
                                status = HttpStatusCode.OK,
                                message = cityResponse.data!!
                            )
                        }
                        is ServiceResult.Error -> {
                            call.respond(
                                status = HttpStatusCode.BadRequest,
                                message = cityResponse.errorCode.message
                            )
                        }
                    }
                }

                if (stateId != null && cityTitle != null) {
                    when (val cityResponse = cityRepository.getCitiesByTitle(stateId, cityTitle)) {
                        is ServiceResult.Success -> {
                            call.respond(
                                status = HttpStatusCode.OK,
                                message = cityResponse.data
                            )
                        }
                        is ServiceResult.Error -> {
                            call.respond(
                                status = HttpStatusCode.BadRequest,
                                message = cityResponse.errorCode.message
                            )
                        }
                    }
                }

                if (stateId != null) {
                    when (val cityResponse = cityRepository.getCities(stateId)) {
                        is ServiceResult.Success -> {
                            call.respond(
                                status = HttpStatusCode.OK,
                                message = cityResponse.data
                            )
                        }
                        is ServiceResult.Error -> {
                            call.respond(
                                status = HttpStatusCode.BadRequest,
                                message = cityResponse.errorCode.message
                            )
                        }
                    }
                }

//                if (cityId == null) {
//                    when (val cities = cityRepository.getCities(stateId!!)) {
//                        is ServiceResult.Success -> {
//                            call.respond(
//                                status = HttpStatusCode.OK,
//                                message = cities.data
//                            )
//                        }
//                        is ServiceResult.Error -> {
//                            call.respond(
//                                status = HttpStatusCode.BadRequest,
//                                message = cities
//                            )
//                        }
//                    }
//                }

            }

            put("/city") {
                val city = call.receive<City>()
                cityRepository.updateCity(city)
            }

            delete("/city") {
                val params = call.request.rawQueryParameters
                val stateId = params["state_id"]?.toInt()
                val cityId = params["city_id"]?.toInt()

                if (stateId != null && cityId != null) {
                    cityRepository.deleteCity(cityId)
                } else if (stateId != null && cityId == null){
                    cityRepository.deleteCitiesOfState(stateId)
                } else if (stateId == null && cityId == null) {
                    cityRepository.deleteCities()
                }
            }
        }
    }
}