package com.example.routings

import com.example.models.Seller
import com.example.repository.SellerRepository
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.sellerRoutes(sellerRepository: SellerRepository) {
    routing {
        route("/sellers") {

            post("/seller") {
                val seller = call.receive<Seller>()
                sellerRepository.insertSeller(seller)
            }

            get("/") {
                val params = call.request.rawQueryParameters

            }
        }
    }
}