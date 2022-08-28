package com.example.routings.user

import com.example.models.User
import com.example.repository.UserRepository
import com.example.utils.Routes
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.updateUser(
    userRepository: UserRepository
) {
    route(Routes.USERS_ROUTE) {
        put(Routes.UPDATE_ROUTE) {
            val id = call.parameters["id"]!!.toLong()
            call.receiveOrNull<User>()?.let { user ->
                userRepository.updateUserById(id, user).let {
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
                                message = "Database Error"
                            )
                        }
                    }
                }
            } ?: kotlin.run {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = "Check your json file"
                )
                return@put
            }
        }
    }
}