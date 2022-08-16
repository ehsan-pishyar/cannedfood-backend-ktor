package com.example.routings.user

import com.example.models.responses.User
import com.example.usecases.InsertUserUseCase
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.InsertNewUser(
    insertUserUseCase: InsertUserUseCase
) {
    route("/users") {
        post("/create") {

            val request = call.receiveOrNull<User>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val userResponse = insertUserUseCase(ServiceResult.Success(request))
            var httpStatus = if (userResponse.errors.isEmpty()) HttpStatusCode.Created else HttpStatusCode.BadRequest

            call.respond(
                status = httpStatus,
                message = userResponse
            )
        }
    }
}