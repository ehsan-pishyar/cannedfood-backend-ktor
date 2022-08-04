package com.example.routings

import com.example.repository.UserRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.userRoutes(userRepository: UserRepository) {
    routing {
        route("/users") {

            get("/") {
                val params = call.request.rawQueryParameters
                val id = params["user_id"]!!.toInt()
                val username = params["username"]

                if (id == null && username == null) {
                    val users = userRepository.getUsers()
                    call.respond(users)
                } else if (id != null && username == null) {
                    val user = userRepository.getUserById(id)
                    call.respond(user)
                } else {
                    val users = userRepository.getUserByUsername(username)
                    call.respond(users)
                }
            }
        }
    }
}