package com.example.routings.user

import com.example.repository.UserRepository
import com.example.usecases.InsertUserUseCase
import com.example.utils.Routes
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.userRoutes(
    userRepository: UserRepository,
    insertUserUseCase: InsertUserUseCase
) {

    routing {
        route(Routes.USERS_ROUTE) {
            insertNewUser(insertUserUseCase)
            getUsers(userRepository)
        }
    }
}