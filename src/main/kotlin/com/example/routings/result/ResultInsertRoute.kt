package com.example.routings.result

import com.example.models.Results
import com.example.usecases.InsertResultUseCase
import com.example.utils.Routes
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.insertNewResult(
    insertResultUseCase: InsertResultUseCase
) {
    route(Routes.RESULT_ROUTE) {
        post(Routes.CREATE_ROUTE) {

            val request = call.receiveOrNull<Results>() ?: kotlin.run {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = "Check your json file"
                )
                return@post
            }

            insertResultUseCase.invoke(ServiceResult.Success(request))?.let {
                call.respond(
                    status = HttpStatusCode.OK,
                    message = it
                )
            }
        }
    }
}