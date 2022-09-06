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

fun Route.updateResultCategory(resultCategoryRepository: ResultCategoryRepository) {
    route(Routes.RESULT_CATEGORY_ROUTE) {
        put(Routes.UPDATE_ROUTE) {

            val id = call.parameters["id"]!!.toInt()

            val resultCategory = call.receiveOrNull<ResultCategory>() ?: kotlin.run {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = it
                )
                return@put
            }

            resultCategoryRepository.updateResultCategory(id, resultCategory).let {
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