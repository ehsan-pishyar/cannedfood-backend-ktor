package com.example.routings.user

import com.example.repository.UserRepository
import com.example.utils.Routes
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getUsers(userRepository: UserRepository) {
    route(Routes.USERS_ROUTE) {
        get("/") {

            val params = call.request.rawQueryParameters
            val id = params["id"]?.toLong()
            val email = params["email"]

            if (params.isEmpty()) {
                userRepository.getUsers().let {
                    when(it) {
                        is ServiceResult.Success -> {
                            call.respond(
                                status = HttpStatusCode.OK,
                                message = it.data
                            )
                        }
                        is ServiceResult.Error -> {
                            call.respond(
                                status = HttpStatusCode.BadRequest,
                                message = it.errorCode.message
                            )
                        }
                    }
                }
            }

            id?.let { userId ->
                userRepository.getUserById(userId).let {
                    when(it) {
                        is ServiceResult.Success -> {
                            call.respond(
                                status = HttpStatusCode.OK,
                                message = it.data
                            )
                        }
                        is ServiceResult.Error -> {
                            call.respond(
                                status = HttpStatusCode.BadRequest,
                                message = it.errorCode.message
                            )
                        }
                    }
                }
            }

            email?.let { userEmail ->
                userRepository.getUsersByEmail(userEmail).let {
                    when(it) {
                        is ServiceResult.Success -> {
                            call.respond(
                                status = HttpStatusCode.OK,
                                message = it.data
                            )
                        }
                        is ServiceResult.Error -> {
                            call.respond(
                                status = HttpStatusCode.BadRequest,
                                message = it.errorCode.message
                            )
                        }
                    }
                }
            }
        }
    }
}