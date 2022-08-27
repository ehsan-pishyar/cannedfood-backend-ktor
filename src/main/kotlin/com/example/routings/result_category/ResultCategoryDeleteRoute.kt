package com.example.routings.result_category

import com.example.repository.ResultCategoryRepository
import com.example.utils.Routes
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteResultCategories(
    resultCategoryRepository: ResultCategoryRepository
) {
    route(Routes.RESULT_CATEGORY_ROUTE) {
        delete("/{id}/delete") {

            val id = call.parameters["id"]!!.toInt()
            resultCategoryRepository.deleteResultCategoryById(id).let { rcResponse ->
                when (rcResponse) {
                    is ServiceResult.Success -> {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = rcResponse.data
                        )
                    }
                    is ServiceResult.Error -> {
                        println("Error! No Result Categories received from database")
                        call.respond(
                            status = HttpStatusCode.BadRequest,
                            message = rcResponse.errorCode
                        )
                    }
                }
            }
        }
    }
}