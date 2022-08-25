package com.example.routings.comment

import com.example.models.SellerComment
import com.example.repository.CommentRepository
import com.example.utils.Routes
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.addNewSellerComment(
    commentRepository: CommentRepository
) {
    route(Routes.SELLERS_ROUTE) {
        post(Routes.COMMENT_SELLER_ADD_ROUTE) {

            val sellerId = call.parameters["seller_id"]!!.toLong()
            val request = call.receiveOrNull<SellerComment>() ?: kotlin.run {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = "Error! Check your json file"
                )
                return@post
            }

            commentRepository.addCommentForSeller(sellerId, request).let { sellerComments ->
                when(sellerComments) {
                    is ServiceResult.Success -> {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = sellerComments.data!!
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