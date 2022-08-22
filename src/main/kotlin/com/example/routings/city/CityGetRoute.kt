package com.example.routings.city

import com.example.repository.CityRepository
import com.example.utils.Routes
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getCities(
    cityRepository: CityRepository
) {
    route(Routes.CITY_ROUTE) {
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
    }
}