package com.example.routings.seller

import com.example.repository.SellerRepository
import com.example.utils.Routes
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteSellers(sellerRepository: SellerRepository) {
    route(Routes.SELLERS_ROUTE) {
        delete(Routes.DELETE_ROUTE) {

            val id = call.parameters["id"]!!.toLong()

            sellerRepository.deleteSellerById(id).let {
                when(it) {
                    is ServiceResult.Success -> {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = it.data!!
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

            sellerRepository.deleteSellers().let {
                when(it) {
                    is ServiceResult.Success -> {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = it.data!!
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