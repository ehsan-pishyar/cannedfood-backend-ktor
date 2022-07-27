package com.example.routings

import com.example.repository.SellerCategoryRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.sellerCategoryRoutes(sellerCategoryRepository: SellerCategoryRepository) {
    routing {
        route("/seller-categories") {

            get("/") {
                val categories = sellerCategoryRepository.getSellerCategories()
                call.respond(categories)
            }
            get("/[id]") {
                val id = call.parameters["id"]?.toInt()
                val category = sellerCategoryRepository.getSellerCategoryById(id!!)
                call.respond(category!!)
            }
            get("/[title]") {
                val title = call.parameters["title"]
                val category = sellerCategoryRepository.getSellerCategoryByTitle(title)
                call.respond(category!!)
            }
        }
    }
}

