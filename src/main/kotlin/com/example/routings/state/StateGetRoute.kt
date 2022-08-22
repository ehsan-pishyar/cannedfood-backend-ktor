package com.example.routings.state

import com.example.repository.StateRepository
import com.example.utils.Routes
import io.ktor.server.routing.*

fun Route.getStates(
    stateRepository: StateRepository
){
    route(Routes.SELLERS_ROUTE) {
        get("/") {

        }
    }
}