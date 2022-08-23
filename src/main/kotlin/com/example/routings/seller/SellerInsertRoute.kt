package com.example.routings.seller

import com.example.usecases.InsertSellerUseCase
import com.example.utils.Routes
import io.ktor.server.routing.*

fun Route.insertNewSeller(
    insertSellerUseCase: InsertSellerUseCase
) {
    route(Routes.SELLERS_ROUTE) {
        post("/create") {

        }
    }
}