package com.example.routings.result_category

import com.example.models.ResultCategory
import com.example.repository.ResultCategoryRepository
import com.example.utils.Routes
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.insertNewResultCategory(resultCategoryRepository: ResultCategoryRepository) {
    route(Routes.RESULT_CATEGORY_ROUTE) {
        post(Routes.CREATE_ROUTE) {

            val request = call.receiveOrNull<ResultCategory>() ?: kotlin.run {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = "Error! Check your json file"
                )
                return@post
            }

            resultCategoryRepository.insertResultCategory(request).let { rcResponse ->
                when (rcResponse) {
                    is ServiceResult.Success -> {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = rcResponse.data
                        )
                    }
                    is ServiceResult.Error -> {
                        call.respond(
                            status = HttpStatusCode.BadRequest,
                            message = rcResponse.errorCode.message
                        )
                    }
                }
            }
        }
    }
}