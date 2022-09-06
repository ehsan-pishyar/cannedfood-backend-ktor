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

fun Route.updateCity(cityRepository: CityRepository) {
    route(Routes.CITY_ROUTE) {
        put(Routes.UPDATE_ROUTE) {

            val id = call.parameters["id"]!!.toInt()

            val city = call.receiveOrNull<City>() ?: kotlin.run {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = "Error! Check your json file"
                )
                return@put
            }

            cityRepository.updateCity(id, city).let { cityResponse ->
                when(cityResponse) {
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
}