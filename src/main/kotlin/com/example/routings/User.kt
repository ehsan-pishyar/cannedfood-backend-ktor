package com.example.routings

import com.example.repository.UserRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.userRoutes(userRepository: UserRepository) {
    routing {
        route("/users") {

            get("/") {
            }
        }
    }
}