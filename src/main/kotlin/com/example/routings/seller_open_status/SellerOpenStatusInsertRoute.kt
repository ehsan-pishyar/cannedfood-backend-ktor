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

fun Route.insertNewSellerOpenHours(sellerOpenStatusRepository: SellerOpenStatusRepository) {
    route(Routes.SELLERS_ROUTE) {
        post(Routes.SELLER_OPEN_STATUS_ADD_ROUTE) {

            val request = call.receiveOrNull<SellerOpenHours>() ?: kotlin.run {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = ErrorCode.JSON_ERROR
                )
                return@post
            }

            sellerOpenStatusRepository.insertSellerOpenHours(request).let {
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

fun Route.insertNewSellerCloseHours(sellerOpenStatusRepository: SellerOpenStatusRepository) {
    route(Routes.SELLERS_ROUTE) {
        post(Routes.SELLER_CLOSE_STATUS_ADD_ROUTE) {

            val request = call.receiveOrNull<SellerCloseHours>() ?: kotlin.run {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = ErrorCode.JSON_ERROR
                )
                return@post
            }

            sellerOpenStatusRepository.insertSellerCloseHours(request).let {
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