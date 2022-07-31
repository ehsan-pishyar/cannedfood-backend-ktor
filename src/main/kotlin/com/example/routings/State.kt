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
                val title = call.request.queryParameters["state_title"]

                if (title == null) {
                    val states = stateRepository.getStates()
                    call.respond(states)
                } else {
                    val state = stateRepository.getStateByTitle(title)
                    call.respond(state)
                }
            }
        }
    }
}