package com.example.routings.location

import com.example.models.responses.StateResponse
import com.example.repository.StateRepository
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.security.Provider.Service

fun Application.stateRoutes(stateRepository: StateRepository) {
    routing {
        route("/states") {

            get("/") {
                val params = call.request.rawQueryParameters
                val stateId = params["state_id"]?.toInt()
                val stateTitle = params["state_title"]

                if (stateId != null) {
                    when (val state = stateRepository.getStateById(stateId)) {
                        is ServiceResult.Success -> {
                            call.respond(
                                status = HttpStatusCode.OK,
                                message = state.data!!
                            )
                        }
                        is ServiceResult.Error -> {
                            call.respond(
                                status = HttpStatusCode.BadRequest,
                                message = ""
                            )
                        }
                    }
                } else {
                    val states = stateRepository.getStates()
                    if (states is ServiceResult.Success) {
                        call.respond(states.data!!)
                    } else {

                    }
                }
            }
        }
    }
}