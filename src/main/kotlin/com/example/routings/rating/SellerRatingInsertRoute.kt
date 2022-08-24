package com.example.routings.rating

import com.example.models.SellerRating
import com.example.repository.RatingRepository
import com.example.utils.Routes
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.insertNewSellerRating(
    ratingRepository: RatingRepository
) {
    route(Routes.RATING_ROUTE) {
        post(Routes.SELLER_RATE_ROUTE) {

            val sellerId = call.parameters["seller_id"]!!.toLong()
            val request = call.receiveOrNull<SellerRating>() ?: kotlin.run {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = "Error! Check your json file"
                )
                return@post
            }

            ratingRepository.addRateToSeller(sellerId, request).let { sellerRating ->
                when(sellerRating) {
                    is ServiceResult.Success -> {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = sellerRating.data!!
                        )
                    }
                    is ServiceResult.Error -> {
                        println("Error")
                        call.respond(
                            status = HttpStatusCode.BadRequest,
                            message = sellerRating.errorCode.message
                        )
                    }
                }
            }
        }
    }
}