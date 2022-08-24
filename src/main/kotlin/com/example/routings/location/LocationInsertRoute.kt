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

fun Route.insertNewLocation(
    locationRepository: LocationRepository
) {
    route(Routes.LOCATION_ROUTE) {
        post("/create") {

            val request = call.receiveOrNull<Location>() ?: kotlin.run {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = "Error! Check your json file"
                )
                return@post
            }
            locationRepository.insertLocation(request).let { locationResponse ->
                when (locationResponse) {
                    is ServiceResult.Success -> {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = locationResponse.data!!
                        )
                    }
                    is ServiceResult.Error -> {
                        println("Error! No Location received from database")
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