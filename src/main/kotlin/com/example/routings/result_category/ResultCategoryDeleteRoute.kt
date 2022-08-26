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
            id.let {
                resultCategoryRepository.deleteResultCategoryById(it).let { rcResponse ->
                    when(rcResponse) {
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

        delete("/{sc_id}/delete") {

            val scId = call.parameters["sc_id"]!!.toInt()
            scId.let {
                resultCategoryRepository.deleteResultCategoriesOfSellerCategory(scId).let { rcResponse ->
                    when(rcResponse) {
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

        delete("/") {
            resultCategoryRepository.deleteResultCategories().let { rcResponse ->
                when(rcResponse) {
                    is ServiceResult.Success -> {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = rcResponse.data
                        )
                    }
                    is ServiceResult.Error -> {
                        println("Error! No Cities received from database")
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