package com.example.routings.user

import com.example.repository.UserRepository
import com.example.utils.Routes
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteUsers(
    userRepository: UserRepository
) {
    route(Routes.USERS_ROUTE) {
        delete("/{id}/delete") {
            val id = call.parameters["id"]!!.toInt()
            userRepository.deleteUser(id)
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
                            message = "No User Found"
                        )
                    }
                }
            }
        }

        delete("/") {
            userRepository.deleteUsers()
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
                            message = "No User Found"
                        )
                    }
                }
            }
        }
    }
}