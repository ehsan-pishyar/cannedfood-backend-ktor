package com.example.routings.rating

import com.example.models.ResultRating
import com.example.repository.RatingRepository
import com.example.utils.Routes
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.rateToResult(
    ratingRepository: RatingRepository
) {
    route(Routes.RESULT_ROUTE) {
        post(Routes.RESULT_RATE_ROUTE) {

            val resultId = call.parameters["result_id"]!!.toLong()
            val request = call.receiveOrNull<ResultRating>() ?: kotlin.run {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = "Error! Check your json file"
                )
                return@post
            }

            ratingRepository.addRateToResult(resultId, request).let { resultRating ->
                when(resultRating) {
                    is ServiceResult.Success -> {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = resultRating.data!!
                        )
                    }
                    is ServiceResult.Error -> {
                        println("Error")
                        call.respond(
                            status = HttpStatusCode.BadRequest,
                            message = resultRating.errorCode.message
                        )
                    }
                }
            }
        }
    }
}