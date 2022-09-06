package com.example.routings.comment

import com.example.repository.CommentRepository
import com.example.utils.Routes
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getSellerComments(commentRepository: CommentRepository) {
    route(Routes.SELLERS_ROUTE) {
        get(Routes.COMMENT_SELLER_GET_ROUTE) {

            val sellerId = call.parameters["seller_id"]!!.toLong()

            commentRepository.getSellerComments(sellerId).let { sellerComments ->
                when(sellerComments) {
                    is ServiceResult.Success -> {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = sellerComments.data
                        )
                    }
                    is ServiceResult.Error -> {
                        call.respond(
                            status = HttpStatusCode.BadRequest,
                            message = sellerComments.errorCode.message
                        )
                    }
                }
            }
        }
    }
}