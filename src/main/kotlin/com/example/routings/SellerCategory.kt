package com.example.routings

import com.example.repository.SellerCategoryRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.sellerCategoryRoutes(sellerCategoryRepository: SellerCategoryRepository) {
    routing {
        route("/seller-categories") {

            get("/") {
                val title = call.request.queryParameters["sc_title"]

                if (title == null) {
                    val sellerCategories = sellerCategoryRepository.getSellersCategories()
                    call.respond(sellerCategories)
                } else {
                    val sellerCategories = sellerCategoryRepository.getSellerCategoryByTitle(title)
                    call.respond(sellerCategories)
                }
            }
        }
    }
}

