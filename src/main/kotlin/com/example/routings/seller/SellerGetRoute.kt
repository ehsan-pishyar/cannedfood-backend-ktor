package com.example.routings.seller

import com.example.repository.SellerRepository
import com.example.utils.Routes
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getSellers(sellerRepository: SellerRepository) {
    route(Routes.SELLERS_ROUTE) {
        get("/") {

            val params = call.request.rawQueryParameters
            val id = params["id"]?.toLong()
            val title = params["title"]
            val description = params["description"]
            val stateId = params["state_id"]?.toInt()
            val cityId = params["city_id"]?.toInt()
            val locationId = params["location_id"]?.toLong()
            val locationTitle = params["location_title"]
            val resultID = params["result_id"]?.toLong()
            val sellerCategoryId = params["sc_id"]?.toInt()
            val resultCategoryId = params["rc_id"]?.toInt()
            val foodCategoryId = params["fc_id"]?.toInt()
            val deliveryFee = params["delivery_fee"]?.toLong()
            val deliveryDuration = params["delivery_duration"]?.toInt()

            if(params.isEmpty()) {
                sellerRepository.getSellers().let {
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

            id?.let { sellerId ->
                sellerRepository.getSellerDetails(sellerId).let {
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

            title?.let { sellerTitle ->
                sellerRepository.getSellersByTitle(sellerTitle).let {
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

            description?.let { sellerDescription ->
                sellerRepository.getSellersByTitle(sellerDescription).let {
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

            stateId?.let { sellerStateId ->
                sellerRepository.getSellersByStateId(sellerStateId).let {
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

            cityId?.let { sellerCityId ->
                sellerRepository.getSellersByCityId(sellerCityId).let {
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

            locationId?.let { sellerLocationId ->
                sellerRepository.getSellersByLocationId(sellerLocationId).let {
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

            locationTitle?.let { sellerLocationTitle ->
                sellerRepository.getSellersByLocationTitle(sellerLocationTitle).let {
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

            resultID?.let { sellerResultId ->
                sellerRepository.getSellerByResultId(sellerResultId).let {
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

            sellerCategoryId?.let { sellerSellerCategoryId ->
                sellerRepository.getSellersBySellerCategoryId(sellerSellerCategoryId).let {
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

            resultCategoryId?.let { sellerResultCategoryId ->
                sellerRepository.getSellersByResultCategoryId(sellerResultCategoryId).let {
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

            foodCategoryId?.let { sellerFoodCategoryId ->
                sellerRepository.getSellersByFoodCategoryId(sellerFoodCategoryId).let {
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

            deliveryFee?.let { sellerDeliveryFee ->
                sellerRepository.getSellersByDeliveryFee(sellerDeliveryFee).let {
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

            deliveryDuration?.let { sellerDeliveryDuration ->
                sellerRepository.getSellersByDeliveryDuration(sellerDeliveryDuration).let {
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
}