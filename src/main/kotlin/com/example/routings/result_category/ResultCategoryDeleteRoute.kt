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
        delete(Routes.DELETE_ROUTE) {

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
                        call.respond(
                            status = HttpStatusCode.BadRequest,
                            message = rcResponse.errorCode
                        )
                    }
                }
            }
        }

        delete("/delete/") {

            val scId = call.request.queryParameters["sc_id"]?.toInt()
            scId?.let {
                resultCategoryRepository.deleteResultCategoriesOfSellerCategory(scId).let {
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
            }?: kotlin.run {
                resultCategoryRepository.deleteResultCategories().let {
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