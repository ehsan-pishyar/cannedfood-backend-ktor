package com.example.routings.food_category

import com.example.repository.FoodCategoryRepository
import com.example.utils.Routes
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteFoodCategories(foodCategoryRepository: FoodCategoryRepository) {
    route(Routes.FOOD_CATEGORY_ROUTE) {
        delete(Routes.DELETE_ROUTE) {

            val id = call.parameters["id"]!!.toInt()

            foodCategoryRepository.deleteFoodCategoryById(id).let { fcResponse ->
                when(fcResponse) {
                    is ServiceResult.Success -> {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = fcResponse.data
                        )
                    }
                    is ServiceResult.Error -> {
                        call.respond(
                            status = HttpStatusCode.BadRequest,
                            message = fcResponse.errorCode.message
                        )
                    }
                }
            }
        }

        delete("/delete/") {

            val rcId = call.parameters["rc_id"]?.toInt()

            rcId?.let {
                foodCategoryRepository.deleteFoodCategoriesOfResultCategory(rcId).let { fcResponse ->
                    when(fcResponse) {
                        is ServiceResult.Success -> {
                            call.respond(
                                status = HttpStatusCode.OK,
                                message = fcResponse.data
                            )
                        }
                        is ServiceResult.Error -> {
                            println("Error! No Food Categories received from database")
                            call.respond(
                                status = HttpStatusCode.BadRequest,
                                message = fcResponse.errorCode.message
                            )
                        }
                    }
                }
            } ?: kotlin.run {
                foodCategoryRepository.deleteFoodCategories().let { fcResponse ->
                    when(fcResponse) {
                        is ServiceResult.Success -> {
                            call.respond(
                                status = HttpStatusCode.OK,
                                message = fcResponse.data
                            )
                        }
                        is ServiceResult.Error -> {
                            call.respond(
                                status = HttpStatusCode.BadRequest,
                                message = fcResponse.errorCode.message
                            )
                        }
                    }
                }
            }
        }
    }
}