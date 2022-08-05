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
                val sellerCategoryId = params["sc_id"]!!.toInt()
                val resultCategoryId = params["rc_id"]!!.toInt()
                val foodCategoryTitle = params["fc_title"]

                if (foodCategoryTitle == null) {
                    val foodCategories = foodCategoryRepository.getFoodCategories(sellerCategoryId, resultCategoryId)
                    call.respond(foodCategories)
                } else {
                    val foodCategories = foodCategoryRepository.getFoodCategoriesByTitle(
                        sellerCategoryId, resultCategoryId, foodCategoryTitle
                    )
                    call.respond(foodCategories)
                }
            }

            put("/food-category") {
                val fc = call.receive<FoodCategory>()
                foodCategoryRepository.updateFoodCategory(
                    fc.seller_category_id,
                    fc.result_category_id,
                    fc.id,
                    fc
                )
            }

            delete("/food-category") {
                val params = call.request.rawQueryParameters
                val scId = params["sc_id"]!!.toInt()
                val rcId = params["rc_id"]!!.toInt()
                val fcId = params["fc_id"]!!.toInt()

                if (scId != null && rcId != null && fcId != null) {
                    foodCategoryRepository.deleteFoodCategory(scId, rcId, fcId)
                } else if (scId != null && rcId != null && fcId == null) {
                    foodCategoryRepository.deleteFoodCategoriesOfResultCategory(scId, rcId)
                } else if (scId == null && rcId == null && fcId == null) {
                    foodCategoryRepository.deleteFoodCategories()
                }
            }
        }
    }
}