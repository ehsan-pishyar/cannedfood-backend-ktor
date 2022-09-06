package com.example.routings.food_category

import com.example.models.FoodCategory
import com.example.repository.FoodCategoryRepository
import com.example.utils.Routes
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.updateFoodCategory(foodCategoryRepository: FoodCategoryRepository) {
    route(Routes.FOOD_CATEGORY_ROUTE) {
        put(Routes.UPDATE_ROUTE) {

            val id = call.parameters["id"]!!.toInt()

            val foodCategory = call.receiveOrNull<FoodCategory>() ?: kotlin.run {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = "Error! Check your json file"
                )
                return@put
            }

            foodCategoryRepository.updateFoodCategory(id, foodCategory).let { fcResponse ->
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