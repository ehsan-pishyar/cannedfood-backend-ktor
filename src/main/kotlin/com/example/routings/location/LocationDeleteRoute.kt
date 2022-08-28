package com.example.routings.location

import com.example.repository.LocationRepository
import com.example.utils.Routes
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteLocations(
    locationRepository: LocationRepository
) {
    route(Routes.LOCATION_ROUTE) {
        delete(Routes.DELETE_ROUTE) {

            val id = call.parameters["id"]!!.toLong()
            id.let {
                locationRepository.deleteLocationById(id).let { locationsResponse ->
                    when(locationsResponse) {
                        is ServiceResult.Success -> {
                            call.respond(
                                status = HttpStatusCode.OK,
                                message = locationsResponse.data
                            )
                        }
                        is ServiceResult.Error -> {
                            call.respond(
                                status = HttpStatusCode.BadRequest,
                                message = locationsResponse.errorCode.message
                            )
                        }
                    }
                }
            }
        }

        delete("/delete/") {

            val cityId = call.parameters["city_id"]?.toInt()
            cityId?.let {
                locationRepository.deleteLocationsOfCity(cityId).let { locationsResponse ->
                    when(locationsResponse) {
                        is ServiceResult.Success -> {
                            call.respond(
                                status = HttpStatusCode.OK,
                                message = locationsResponse.data
                            )
                        }
                        is ServiceResult.Error -> {
                            call.respond(
                                status = HttpStatusCode.BadRequest,
                                message = locationsResponse.errorCode.message
                            )
                        }
                    }
                }
            } ?: kotlin.run {
                locationRepository.deleteLocations().let { locationsResponse ->
                    when(locationsResponse) {
                        is ServiceResult.Success -> {
                            call.respond(
                                status = HttpStatusCode.OK,
                                message = locationsResponse.data
                            )
                        }
                        is ServiceResult.Error -> {
                            call.respond(
                                status = HttpStatusCode.BadRequest,
                                message = locationsResponse.errorCode.message
                            )
                        }
                    }
                }
            }
        }
    }
}