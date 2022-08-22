package com.example.routings

import com.example.models.Seller
import com.example.repository.SellerRepository
import com.example.usecases.InsertSellerUseCase
import com.example.utils.ServiceResult
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.sellerRoutes(
    sellerRepository: SellerRepository,
    insertSellerUseCase: InsertSellerUseCase
) {
    routing {
        route("/sellers") {

            post("/seller") {
                call.receiveOrNull<Seller>()?.let { request ->
                    insertSellerUseCase.invoke(ServiceResult.Success(request))?.let { seller ->
                        call.respond(seller)
                    } ?: kotlin.run {
                        call.respondText("Error! Nothing returned from usecase")
                        return@post
                    }
                } ?: kotlin.run {
                    call.respondText("Error! Check your json fields")
                    return@post
                }
            }

            get("/") {
                val params = call.request.rawQueryParameters

            }
        }
    }
}