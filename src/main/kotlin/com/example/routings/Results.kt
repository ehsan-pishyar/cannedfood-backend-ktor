package com.example.routings

import com.example.repository.ResultsRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.resultsRoutes(resultsRepository: ResultsRepository) {
    routing {
        route("/results") {

            get("/") {
            }
        }
    }
}