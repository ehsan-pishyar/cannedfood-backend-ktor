package com.example.routings.comment

import com.example.repository.CommentRepository
import com.example.utils.Routes
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getResultComments(commentRepository: CommentRepository) {
    route(Routes.RESULT_ROUTE) {
        get(Routes.COMMENT_RESULT_GET_ROUTE) {

            val resultId = call.parameters["result_id"]!!.toLong()

            commentRepository.getResultComments(resultId).let { resultComments ->
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