package com.example.routings.seller

import com.example.models.Seller
import com.example.repository.SellerRepository
import com.example.utils.ErrorCode
import com.example.utils.Routes
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.updateSeller(sellerRepository: SellerRepository) {
    route(Routes.SELLERS_ROUTE) {
        put(Routes.UPDATE_ROUTE) {

            val id = call.parameters["id"]!!.toLong()

            call.receiveOrNull<Seller>()?.let { seller ->
                sellerRepository.updateSeller(id, seller).let {
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
            } ?: kotlin.run {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = ErrorCode.JSON_ERROR
                )
                return@put
            }
        }
    }
}