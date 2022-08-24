package com.example.routings.seller_category

import com.example.repository.SellerCategoryRepository
import com.example.utils.Routes
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteSellerCategories(
    sellerCategoryRepository: SellerCategoryRepository
) {
    route(Routes.SELLER_CATEGORY_ROUTE) {
        delete(Routes.DELETE_ROUTE) {

            val id = call.parameters["id"]!!.toInt()
            id.let {
                sellerCategoryRepository.deleteSellerCategory(it).let { scResponse ->
                    when(scResponse) {
                        is ServiceResult.Success -> {
                            call.respond(
                                status = HttpStatusCode.OK,
                                message = scResponse.data
                            )
                        }
                        is ServiceResult.Error -> {
                            println("Error! No Seller Categories received from database")
                            call.respond(
                                status = HttpStatusCode.BadRequest,
                                message = scResponse.errorCode
                            )
                        }
                    }
                }
            }
        }

        delete("/") {
            sellerCategoryRepository.deleteSellerCategories().let { scResponse ->
                when(scResponse) {
                    is ServiceResult.Success -> {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = scResponse.data
                        )
                    }
                    is ServiceResult.Error -> {
                        println("Error! No Seller Categories received from database")
                        call.respond(
                            status = HttpStatusCode.BadRequest,
                            message = scResponse.errorCode
                        )
                    }
                }
            }
        }
    }
}