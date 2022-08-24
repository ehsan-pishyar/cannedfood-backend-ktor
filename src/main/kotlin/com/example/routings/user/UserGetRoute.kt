package com.example.routings.user

import com.example.repository.UserRepository
import com.example.utils.Routes
import com.example.utils.ServiceResult
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getUsers(
    userRepository: UserRepository
) {
    route(Routes.USERS_ROUTE) {
        get("/") {
            val params = call.request.rawQueryParameters
            val id = params["id"]?.toLong()
            val email = params["email"]

            id?.let { userId ->
                userRepository.getUserById(userId).let {
                    when(it) {
                        is ServiceResult.Success -> call.respond(it.data!!)
                        else -> call.respondText("UNKNOWN ERROR")
                    }
                }
            }

            email?.let { userEmail ->
                userRepository.getUsersByEmail(userEmail).let {
                    when(it) {
                        is ServiceResult.Success -> call.respond(it.data)
                        else -> call.respondText("UNKNOWN ERROR")
                    }
                }
            }

            if (id == null && email == null) {
                userRepository.getUsers().let {
                    when(it) {
                        is ServiceResult.Success -> call.respond(it.data)
                        else -> call.respondText("UNKNOWN ERROR")
                    }
                }
            }
        }
    }
}