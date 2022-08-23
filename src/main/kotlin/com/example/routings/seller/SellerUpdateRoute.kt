package com.example.routings.seller

import com.example.repository.SellerRepository
import com.example.utils.Routes
import io.ktor.server.routing.*

fun Route.updateSeller(
    sellerRepository: SellerRepository
) {
    route(Routes.SELLERS_ROUTE) {
        put("/{id}/update") {

        }
    }
}