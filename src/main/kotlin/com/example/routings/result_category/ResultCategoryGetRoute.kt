package com.example.routings.result_category

import com.example.repository.ResultCategoryRepository
import com.example.utils.Routes
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getResultCategories(resultCategoryRepository: ResultCategoryRepository){
    route(Routes.RESULT_CATEGORY_ROUTE){
        get("/") {

            val params = call.request.rawQueryParameters
            val scId = params["sc_id"]?.toInt()
            val id = params["id"]?.toInt()
            val title = params["title"]

            if (id == null && title == null) {
                resultCategoryRepository.getResultCategories(scId!!).let { rcResponse ->
                    when(rcResponse) {
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

            id?.let { rcId ->
                resultCategoryRepository.getResultCategoryById(rcId).let {
                    when(it){
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

            title?.let { rcTitle ->
                resultCategoryRepository.getResultCategoriesByTitle(rcTitle).let { rcResponse ->
                    when(rcResponse) {
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
        }
    }
}