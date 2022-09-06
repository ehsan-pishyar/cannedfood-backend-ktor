package com.example.routings.comment

import com.example.models.ResultComment
import com.example.repository.CommentRepository
import com.example.utils.Routes
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.addNewResultComment(
    commentRepository: CommentRepository
) {
    route(Routes.RESULT_ROUTE) {
        post(Routes.COMMENT_RESULT_ADD_ROUTE) {

            val resultId = call.parameters["result_id"]!!.toLong()

            val request = call.receiveOrNull<ResultComment>() ?: kotlin.run {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = "Error! Check your json file"
                )
                return@post
            }

            commentRepository.addCommentForResult(resultId, request).let { resultComments ->
                when(resultComments) {
                    is ServiceResult.Success -> {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = resultComments.data
                        )
                    }
                    is ServiceResult.Error -> {
                        call.respond(
                            status = HttpStatusCode.BadRequest,
                            message = resultComments.errorCode.message
                        )
                    }
                }
            }
        }
    }
}