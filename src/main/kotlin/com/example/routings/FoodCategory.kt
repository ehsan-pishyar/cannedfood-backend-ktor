package com.example.routings

import com.example.models.FoodCategory
import com.example.repository.FoodCategoryRepository
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.typeRoutes(foodCategoryRepository: FoodCategoryRepository) {
    routing {
        route("/food-categories") {

            post("/food-category") {
                val fc = call.receive<FoodCategory>()
                foodCategoryRepository.insertFoodCategory(fc)
            }

            get("/") {
                val params = call.request.rawQueryParameters
                val resultCategoryId = params["rc_id"]?.toInt()
                val foodCategoryTitle = params["fc_title"]

                if (foodCategoryTitle == null) {
                    val foodCategories = foodCategoryRepository.getFoodCategories(resultCategoryId!!)
                    call.respond(foodCategories)
                } else {
                    val foodCategories = foodCategoryRepository.getFoodCategoriesByTitle(
                        foodCategoryTitle
                    )
                    call.respond(foodCategories)
                }
            }

            put("/food-category") {
                val fc = call.receive<FoodCategory>()
                foodCategoryRepository.updateFoodCategory(
                    fc.id,
                    fc
                )
            }

            delete("/food-category") {
                val params = call.request.rawQueryParameters
                val rcId = params["rc_id"]?.toInt()
                val fcId = params["fc_id"]?.toInt()

                if (rcId != null && fcId != null) {
                    foodCategoryRepository.deleteFoodCategory(rcId)
                }else if (rcId != null && fcId == null) {
                    foodCategoryRepository.deleteFoodCategoriesOfResultCategory(rcId)
                }else if (rcId == null && fcId == null) {
                    foodCategoryRepository.deleteFoodCategories()
                }
            }
        }
    }
}