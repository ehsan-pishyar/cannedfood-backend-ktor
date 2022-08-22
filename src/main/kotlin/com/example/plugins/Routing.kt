package com.example.plugins

import com.example.datasource.UserRepositoryImpl
import com.example.repository.UserRepository
import com.example.routings.user.deleteUsers
import com.example.routings.user.getUsers
import com.example.routings.user.insertNewUser
import com.example.routings.user.updateUser
import com.example.usecases.InsertUserUseCase
import com.example.utils.ValidateUserEmail
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*

fun Application.configureRouting() {
    routing {
        route("/") {
            get {
                call.respondText("Hello")
            }

            // User Section
            val userRepository: UserRepository = UserRepositoryImpl()
            val emailValidation = ValidateUserEmail()
            val insertUserUseCase = InsertUserUseCase(emailValidation, userRepository)
            insertNewUser(insertUserUseCase)
            getUsers(userRepository)
            updateUser(userRepository)
            deleteUsers(userRepository)


        }
        // Static plugin. Try to access `/static/index.html`
        static("/static") {
            resources("static")
        }
    }
}
