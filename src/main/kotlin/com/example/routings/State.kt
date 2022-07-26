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

            get("/all") {
                val states = stateRepository.getStates()
                call.respond(states)
            }
            get("/[id]") {
                val stateId = call.parameters["id"]?.toInt()
                val state = stateRepository.getStateById(stateId!!)
                call.respond(state!!)
            }
            get("/[title]") {
                val stateTitle = call.parameters["title"]
                val state = stateRepository.getStateByTitle(stateTitle!!)
                call.respond(state!!)
            }

        }
    }
}