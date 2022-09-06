package com.example.routings.location

import com.example.models.Location
import com.example.repository.LocationRepository
import com.example.utils.Routes
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.updateLocation(locationRepository: LocationRepository) {
    route(Routes.LOCATION_ROUTE) {
        put(Routes.UPDATE_ROUTE) {

            val id = call.parameters["id"]!!.toLong()

            val location = call.receiveOrNull<Location>() ?: kotlin.run {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = "Error! Check your json file"
                )
                return@put
            }

            locationRepository.updateLocation(id, location).let { locationResponse ->
                when(locationResponse) {
                    is ServiceResult.Success -> {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = locationResponse.data!!
                        )
                    }
                    is ServiceResult.Error -> {
                        call.respond(
                            status = HttpStatusCode.BadRequest,
                            message = locationResponse.errorCode
                        )
                    }
                }
            }
        }
    }
}