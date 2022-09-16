package com.example.routings.seller_open_status

import com.example.models.SellerCloseHours
import com.example.models.SellerOpenHours
import com.example.repository.SellerOpenStatusRepository
import com.example.utils.ErrorCode
import com.example.utils.Routes
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.updateSellerOpenHoursById(sellerOpenStatusRepository: SellerOpenStatusRepository) {
    route(Routes.SELLERS_ROUTE) {
        put(Routes.SELLER_OPEN_STATUS_UPDATE_ROUTE) {

            val sellerId = call.parameters["seller_id"]!!.toLong()

            val request = call.receiveOrNull<SellerOpenHours>() ?: kotlin.run {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = ErrorCode.JSON_ERROR
                )
                return@put
            }

            sellerOpenStatusRepository.updateSellerOpenHours(sellerId, request).let {
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

fun Route.updateSellerCloseHoursById(sellerOpenStatusRepository: SellerOpenStatusRepository) {
    route(Routes.SELLERS_ROUTE) {
        put(Routes.SELLER_CLOSE_STATUS_UPDATE_ROUTE) {

            val sellerId = call.parameters["seller_id"]!!.toLong()

            val request = call.receiveOrNull<SellerCloseHours>() ?: kotlin.run {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = ErrorCode.JSON_ERROR
                )
                return@put
            }

            sellerOpenStatusRepository.updateSellerCloseHours(sellerId, request).let {
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