package com.example.routings.food_category

import com.example.repository.FoodCategoryRepository
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getFoodCategories(
    foodCategoryRepository: FoodCategoryRepository
) {
    get("/") {

        val params = call.request.rawQueryParameters
        val rcId = params["rc_id"]?.toInt()
        val id = params["id"]?.toInt()
        val title = params["title"]

        if (rcId != null && id == null && title == null) {
            foodCategoryRepository.getFoodCategories(rcId).let { fcResponse ->
                when(fcResponse) {
                    is ServiceResult.Success -> {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = fcResponse.data
                        )
                    }
                    is ServiceResult.Error -> {
                        println("Error! Food Category not found")
                        call.respond(
                            status = HttpStatusCode.BadRequest,
                            message = fcResponse.errorCode
                        )
                    }
                }
            }
        }

        if (rcId != null && id != null) {
            foodCategoryRepository.getFoodCategoryById(id).let { fcResponse ->
                when(fcResponse) {
                    is ServiceResult.Success -> {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = fcResponse.data
                        )
                    }
                    is ServiceResult.Error -> {
                        println("Error! Food Category not found")
                        call.respond(
                            status = HttpStatusCode.BadRequest,
                            message = fcResponse.errorCode
                        )
                    }
                }
            }
        }

        if (rcId != null && title != null) {
            foodCategoryRepository.getFoodCategoriesByTitle(title).let { fcResponse ->
                when(fcResponse) {
                    is ServiceResult.Success -> {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = fcResponse.data
                        )
                    }
                    is ServiceResult.Error -> {
                        println("Error! Food Category not found")
                        call.respond(
                            status = HttpStatusCode.BadRequest,
                            message = fcResponse.errorCode
                        )
                    }
                }
            }
        }
    }
}