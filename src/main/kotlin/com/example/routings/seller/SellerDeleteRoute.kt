package com.example.routings.seller

import com.example.repository.SellerRepository
import com.example.utils.Routes
import io.ktor.server.routing.*

fun Route.deleteSellers(
    sellerRepository: SellerRepository
) {
    route(Routes.SELLERS_ROUTE) {
        delete("/{id}/delete") {

        }

        delete("/") {

        }
    }
}