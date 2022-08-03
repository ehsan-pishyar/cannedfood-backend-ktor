package com.example.routings

import com.example.repository.FoodCategoryRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.typeRoutes(foodCategoryRepository: FoodCategoryRepository) {
    routing {
        route("/food-categories") {

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
        }
    }
}