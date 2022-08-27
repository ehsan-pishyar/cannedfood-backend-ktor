package com.example.routings.seller

import com.example.repository.SellerRepository
import com.example.utils.Routes
import com.example.utils.ServiceResult
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getSellers(
    sellerRepository: SellerRepository
) {
    route(Routes.SELLERS_ROUTE) {
        get("/") {

            val params = call.request.rawQueryParameters
            val id = params["id"]?.toLong()
            val title = params["title"]
            val description = params["description"]
            val stateId = params["state_id"]?.toInt()
            val cityId = params["city_id"]?.toInt()
            val locationId = params["location_id"]?.toLong()
            val sellerCategoryId = params["seller_category_id"]?.toInt()
            val resultCategoryId = params["result_category_id"]?.toInt()
            val foodCategoryId = params["food_category_id"]?.toInt()
            val deliveryFee = params["delivery_fee"]?.toLong()
            val deliveryDuration = params["delivery_duration"]?.toInt()

            id?.let { sellerId ->
                sellerRepository.getSellerDetails(sellerId).let {
                    when(it) {
                        is ServiceResult.Success -> call.respond(it.data)
                        else -> call.respondText("UNKNOWN ERROR")
                    }
                }
            }

            title?.let { sellerTitle ->
                sellerRepository.getSellersByTitle(sellerTitle).let {
                    when(it) {
                        is ServiceResult.Success -> call.respond(it.data)
                        else -> call.respondText("UNKNOWN ERROR")
                    }
                }
            }

            description?.let { sellerDescription ->
                sellerRepository.getSellersByTitle(sellerDescription).let {
                    when(it) {
                        is ServiceResult.Success -> call.respond(it.data)
                        else -> call.respondText("UNKNOWN ERROR")
                    }
                }
            }

            stateId?.let { sellerStateId ->
                sellerRepository.getSellersByStateId(sellerStateId).let {
                    when(it) {
                        is ServiceResult.Success -> call.respond(it.data)
                        else -> call.respondText("UNKNOWN ERROR")
                    }
                }
            }

            cityId?.let { sellerCityId ->
                sellerRepository.getSellersByCityId(sellerCityId).let {
                    when(it) {
                        is ServiceResult.Success -> call.respond(it.data)
                        else -> call.respondText("UNKNOWN ERROR")
                    }
                }
            }

            locationId?.let { sellerLocationId ->
                sellerRepository.getSellersByLocationId(sellerLocationId).let {
                    when(it) {
                        is ServiceResult.Success -> call.respond(it.data)
                        else -> call.respondText("UNKNOWN ERROR")
                    }
                }
            }

            sellerCategoryId?.let { sellerSellerCategoryId ->
                sellerRepository.getSellersBySellerCategoryId(sellerSellerCategoryId).let {
                    when(it) {
                        is ServiceResult.Success -> call.respond(it.data)
                        else -> call.respondText("UNKNOWN ERROR")
                    }
                }
            }

            resultCategoryId?.let { sellerResultCategoryId ->
                sellerRepository.getSellersByResultCategoryId(sellerResultCategoryId).let {
                    when(it) {
                        is ServiceResult.Success -> call.respond(it.data)
                        else -> call.respondText("UNKNOWN ERROR")
                    }
                }
            }

            foodCategoryId?.let { sellerFoodCategoryId ->
                sellerRepository.getSellersByFoodCategoryId(sellerFoodCategoryId).let {
                    when(it) {
                        is ServiceResult.Success -> call.respond(it.data)
                        else -> call.respondText("UNKNOWN ERROR")
                    }
                }
            }

            deliveryFee?.let { sellerDeliveryFee ->
                sellerRepository.getSellersByDeliveryFee(sellerDeliveryFee).let {
                    when(it) {
                        is ServiceResult.Success -> call.respond(it.data)
                        else -> call.respondText("UNKNOWN ERROR")
                    }
                }
            }

            deliveryDuration?.let { sellerDeliveryDuration ->
                sellerRepository.getSellersByDeliveryDuration(sellerDeliveryDuration).let {
                    when(it) {
                        is ServiceResult.Success -> call.respond(it.data)
                        else -> call.respondText("UNKNOWN ERROR")
                    }
                }
            }

            if (params.isEmpty()) {
                sellerRepository.getSellers().let {
                    when(it) {
                        is ServiceResult.Success -> call.respond(it.data)
                        else -> call.respondText("UNKNOWN ERROR")
                    }
                }
            }
        }
    }
}