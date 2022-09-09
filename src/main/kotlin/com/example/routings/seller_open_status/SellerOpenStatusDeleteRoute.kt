package com.example.routings.seller_open_status

import com.example.repository.SellerOpenStatusRepository
import com.example.utils.Routes
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteOpenStatusBySellerId(sellerOpenStatusRepository: SellerOpenStatusRepository) {
    route(Routes.SELLERS_ROUTE) {
        delete(Routes.SELLER_OPEN_STATUS_DELETE_ROUTE) {

            val sellerId = call.parameters["seller_id"]!!.toLong()

            sellerOpenStatusRepository.deleteSellerOpenHoursById(sellerId).let {
                when(it) {
                    is ServiceResult.Success -> {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = it.data
                        )
                    }
                    is ServiceResult.Error ->{
                        call.respond(
                            status = HttpStatusCode.BadRequest,
                            message = it
                        )
                    }
                }
            }
        }
    }
}

fun Route.deleteCloseStatusBySellerId(sellerOpenStatusRepository: SellerOpenStatusRepository) {
    route(Routes.SELLERS_ROUTE) {
        delete(Routes.SELLER_CLOSE_STATUS_DELETE_ROUTE) {

            val sellerId = call.parameters["seller_id"]!!.toLong()

            sellerOpenStatusRepository.deleteSellerCloseHoursById(sellerId).let {
                when(it) {
                    is ServiceResult.Success -> {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = it.data
                        )
                    }
                    is ServiceResult.Error ->{
                        call.respond(
                            status = HttpStatusCode.BadRequest,
                            message = it
                        )
                    }
                }
            }
        }
    }
}

fun Route.deleteAllOpenStatus(sellerOpenStatusRepository: SellerOpenStatusRepository) {
    route(Routes.SELLER_OPEN_STATUS_ROUTE) {
        delete("/delete/") {

            sellerOpenStatusRepository.deleteSellersOpenHours().let {
                when(it) {
                    is ServiceResult.Success -> {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = it.data
                        )
                    }
                    is ServiceResult.Error ->{
                        call.respond(
                            status = HttpStatusCode.BadRequest,
                            message = it
                        )
                    }
                }
            }
        }
    }
}

fun Route.deleteAllCloseStatus(sellerOpenStatusRepository: SellerOpenStatusRepository) {
    route(Routes.SELLER_CLOSE_STATUS_ROUTE) {
        delete("/delete/") {

            sellerOpenStatusRepository.deleteSellersCloseHours().let {
                when(it) {
                    is ServiceResult.Success -> {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = it.data
                        )
                    }
                    is ServiceResult.Error ->{
                        call.respond(
                            status = HttpStatusCode.BadRequest,
                            message = it
                        )
                    }
                }
            }
        }
    }
}