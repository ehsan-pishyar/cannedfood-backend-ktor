package com.example.routings.food_category

import com.example.models.FoodCategory
import com.example.models.ResultCategory
import com.example.repository.FoodCategoryRepository
import com.example.utils.Routes
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.insertNewFoodCategory(
    foodCategoryRepository: FoodCategoryRepository
) {
    route(Routes.FOOD_CATEGORY_ROUTE) {
        post(Routes.CREATE_ROUTE) {

            val request = call.receiveOrNull<FoodCategory>() ?: kotlin.run {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = "Error! Check your json file"
                )
                return@post
            }

            foodCategoryRepository.insertFoodCategory(request).let { fcResponse ->
                when (fcResponse) {
                    is ServiceResult.Success -> {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = fcResponse.data
                        )
                    }
                    is ServiceResult.Error -> {
                        println("Error! No Food Category received from database")
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