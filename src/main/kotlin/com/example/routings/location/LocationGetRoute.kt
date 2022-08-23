package com.example.routings.location

import com.example.repository.LocationRepository
import com.example.utils.Routes
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getLocations(
    locationRepository: LocationRepository
) {
    route(Routes.LOCATION_ROUTE) {
        get("/") {

            val params = call.request.rawQueryParameters
            val cityId = params["city_id"]?.toInt()
            val id = params["id"]?.toLong()
            val title = params["title"]

            if (cityId != null && id == null && title == null) {
                locationRepository.getLocations(cityId).let { locationResponse ->
                    when(locationResponse) {
                        is ServiceResult.Success -> {
                            call.respond(
                                status = HttpStatusCode.OK,
                                message = locationResponse.data
                            )
                        }
                        is ServiceResult.Error -> {
                            println("Error! City not found or state id you entered is invalid")
                            call.respond(
                                status = HttpStatusCode.BadRequest,
                                message = locationResponse.errorCode
                            )
                        }
                    }
                }
            }

            if (cityId != null && id != null) {
                locationRepository.getLocationById(id).let { locationResponse ->
                    when(locationResponse) {
                        is ServiceResult.Success -> {
                            call.respond(
                                status = HttpStatusCode.OK,
                                message = locationResponse.data!!
                            )
                        }
                        is ServiceResult.Error -> {
                            println("Error! City not found")
                            call.respond(
                                status = HttpStatusCode.BadRequest,
                                message = locationResponse.errorCode
                            )
                        }
                    }
                }
            }

            if (cityId == null && title != null) {
                locationRepository.getLocationsByTitle(title).let { locationsResponse ->
                    when(locationsResponse) {
                        is ServiceResult.Success -> {
                            call.respond(
                                status = HttpStatusCode.OK,
                                message = locationsResponse.data
                            )
                        }
                        is ServiceResult.Error -> {
                            println("Error! City not found")
                            call.respond(
                                status = HttpStatusCode.BadRequest,
                                message = locationsResponse.errorCode
                            )
                        }
                    }
                }
            }
        }
    }
}