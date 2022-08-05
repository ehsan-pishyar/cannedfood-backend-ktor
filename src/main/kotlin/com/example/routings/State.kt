package com.example.routings

import com.example.models.State
import com.example.repository.StateRepository
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.stateRoutes(stateRepository: StateRepository) {
    routing {
        route("/states") {

            get("/") {
                val params = call.request.rawQueryParameters
                val stateTitle = params["state_title"]

                if (stateTitle == null) {
                    val states = stateRepository.getStates()
                    call.respond(states)
                } else {
                    val state = stateRepository.getStatesByTitle(stateTitle)
                    call.respond(state)
                }
            }
        }
    }
}