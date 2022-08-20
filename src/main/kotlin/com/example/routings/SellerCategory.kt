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
                    val sellerCategories = sellerCategoryRepository.getSellerCategoriesByTitle(title)
                    call.respond(sellerCategories)
                }
            }

            delete("/seller-category") {
                val scId = call.request.queryParameters["sc_id"]?.toInt()

                if (scId != null) {
                    sellerCategoryRepository.deleteSellerCategory(scId)
                } else {
                    sellerCategoryRepository.deleteSellerCategories()
                }
            }
        }
    }
}

