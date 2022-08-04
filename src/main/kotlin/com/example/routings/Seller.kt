package com.example.routings

import com.example.repository.SellerRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.sellerRoutes(sellerRepository: SellerRepository) {
    routing {
        route("/seller-details") {

            get("/") {
            }
        }
    }
}