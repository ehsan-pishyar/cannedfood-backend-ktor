package com.example.routings.seller

import com.example.models.Seller
import com.example.usecases.InsertSellerUseCase
import com.example.utils.Routes
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.insertNewSeller(
    insertSellerUseCase: InsertSellerUseCase
) {
    route(Routes.SELLERS_ROUTE) {
        post(Routes.CREATE_ROUTE) {

            val request = call.receiveOrNull<Seller>() ?: kotlin.run {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = "Error! Check Json file"
                )
                return@post
            }

            insertSellerUseCase.invoke(request).let {
                call.respond(
                    status = HttpStatusCode.OK,
                    message = it!!
                )
            }
        }
    }
}