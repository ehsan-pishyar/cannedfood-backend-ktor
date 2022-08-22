package com.example.routings.city

import com.example.models.City
import com.example.repository.CityRepository
import com.example.utils.Routes
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.insertCity(
    cityRepository: CityRepository
) {
    route(Routes.CITY_ROUTE) {
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
    }
}