package com.example.routings.seller_open_status

import com.example.repository.SellerOpenStatusRepository
import com.example.utils.Routes
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getSellerOpenStatus(sellerOpenStatusRepository: SellerOpenStatusRepository) {
    route(Routes.SELLERS_ROUTE) {
        get(Routes.SELLER_OPEN_STATUS_GET_ROUTE) {

            val sellerId = call.parameters["seller_id"]!!.toLong()

            sellerOpenStatusRepository.getSellerOpenHoursStatusById(sellerId).let {
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