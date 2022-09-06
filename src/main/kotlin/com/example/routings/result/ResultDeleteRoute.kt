package com.example.routings.result

import com.example.repository.ResultsRepository
import com.example.utils.Routes
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteResults(resultsRepository: ResultsRepository) {
    route(Routes.RESULT_ROUTE) {
        delete(Routes.DELETE_ROUTE) {

            val id = call.parameters["id"]!!.toLong()
            val sellerId = call.request.queryParameters["seller_id"]!!.toLong()

            resultsRepository.deleteResultById(id, sellerId).let {
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

        delete("/delete/") {

            val sellerId = call.request.queryParameters["seller_id"]!!.toLong()

            resultsRepository.deleteResults(sellerId).let {
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