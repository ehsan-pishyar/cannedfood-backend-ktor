package com.example.routings.result_category

import com.example.repository.ResultCategoryRepository
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getResultCategories(
    resultCategoryRepository: ResultCategoryRepository
){
    get("/") {

        val params = call.request.rawQueryParameters
        val scId = params["sc_id"]?.toInt()
        val id = params["id"]?.toInt()
        val title = params["title"]

        if (scId != null && id == null && title == null) {
            resultCategoryRepository.getResultCategories(scId).let { rcResponse ->
                when(rcResponse) {
                    is ServiceResult.Success -> {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = rcResponse.data
                        )
                    }
                    is ServiceResult.Error -> {
                        println("Error! Result Category not found")
                        call.respond(
                            status = HttpStatusCode.BadRequest,
                            message = rcResponse.errorCode
                        )
                    }
                }
            }
        }

        if (scId != null && id != null) {
            resultCategoryRepository.getResultCategoryById(id).let { rcResponse ->
                when(rcResponse) {
                    is ServiceResult.Success -> {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = rcResponse.data
                        )
                    }
                    is ServiceResult.Error -> {
                        println("Error! Result Category not found")
                        call.respond(
                            status = HttpStatusCode.BadRequest,
                            message = rcResponse.errorCode
                        )
                    }
                }
            }
        }

        if (scId != null && title != null) {
            resultCategoryRepository.getResultCategoriesByTitle(title).let { rcResponse ->
                when(rcResponse) {
                    is ServiceResult.Success -> {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = rcResponse.data
                        )
                    }
                    is ServiceResult.Error -> {
                        println("Error! Result Category not found")
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