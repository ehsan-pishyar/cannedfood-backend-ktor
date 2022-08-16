package com.example.routings

import com.example.models.responses.User
import com.example.repository.UserRepository
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.userRoutes(userRepository: UserRepository) {
    routing {
        route("/users") {

            post("/user") {
                val user = call.receive<User>()
                userRepository.insertUser(user)
            }

            get("/") {
                val params = call.request.rawQueryParameters
                val id = params["user_id"]?.toInt()
                val username = params["username"]
                val isAdmin = params["is_admin"]?.toBoolean()
                val isSeller = params["is_seller"]?.toBoolean()
                val isCustomer = params["is_customer"]?.toBoolean()

                if (id != null && username == null) {
                    val user = userRepository.getUserById(id)
                    call.respond(user)
                }

                if (id == null && username != null) {
                    val users = userRepository.getUserByUsername(username)
                    call.respond(users)
                }

            }

            put("/user") {
                val user = call.receive<User>()
                userRepository.updateUser(user.id, user)
            }

            delete("/user") {
                val userId = call.request.queryParameters["user_id"]?.toInt()
                if (userId != null) {
                    userRepository.deleteUser(userId)
                } else {
                    userRepository.deleteUsers()
                }
            }
        }
    }
}