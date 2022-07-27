package com.example.routings

import com.example.repository.UserRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.userRoutes(userRepository: UserRepository) {
    routing {
        route("/users") {

            get("/") {
                val users = userRepository.getUsers()
                call.respond(users)
            }
            get("/[id]") {
                val userId = call.parameters["id"]?.toInt()
                val user = userRepository.getUserById(userId!!)
                call.respond(user!!)
            }
            get("/[username]") {
                val username = call.parameters["username"]
                val user = userRepository.getUserByUsername(username)
                call.respond(user!!)
            }
        }
    }
}