package com.example.routings.state

import com.example.repository.StateRepository
import com.example.utils.Routes
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getStates(
    stateRepository: StateRepository
){
    route(Routes.STATE_ROUTE) {
        get("/") {
            val params = call.request.rawQueryParameters
            val id = params["id"]?.toInt()
            val title = params["title"]

            id?.let { stateId ->
                stateRepository.getStateById(stateId).let { stateResponse ->
                    when(stateResponse) {
                        is ServiceResult.Success -> {
                            call.respond(
                                status = HttpStatusCode.OK,
                                message = stateResponse.data
                            )
                        }
                        is ServiceResult.Error -> {
                            println("Error! State not found")
                            call.respond(
                                status = HttpStatusCode.BadRequest,
                                message = stateResponse.errorCode.message
                            )
                        }
                    }
                }
            }

            title?.let { stateTitle ->
                stateRepository.getStatesByTitle(stateTitle).let { stateResponse ->
                    when(stateResponse) {
                        is ServiceResult.Success -> {
                            call.respond(
                                status = HttpStatusCode.OK,
                                message = stateResponse.data
                            )
                        }
                        is ServiceResult.Error -> {
                            println("Error! State not found")
                            call.respond(
                                status = HttpStatusCode.BadRequest,
                                message = stateResponse.errorCode.message
                            )
                        }
                    }
                }
            }

            if (id == null && title == null) {
                stateRepository.getStates().let { statesResponse ->
                    when(statesResponse) {
                        is ServiceResult.Success -> {
                            call.respond(
                                status = HttpStatusCode.OK,
                                message = statesResponse.data
                            )
                        }
                        is ServiceResult.Error -> {
                            println("Error! No States in database")
                            call.respond(
                                status = HttpStatusCode.BadRequest,
                                message = statesResponse.errorCode.message
                            )
                        }
                    }
                }
            }
        }
    }
}