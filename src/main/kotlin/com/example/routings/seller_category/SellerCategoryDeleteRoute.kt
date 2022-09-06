package com.example.routings.seller_category

import com.example.repository.SellerCategoryRepository
import com.example.utils.Routes
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteSellerCategories(sellerCategoryRepository: SellerCategoryRepository) {
    route(Routes.SELLER_CATEGORY_ROUTE) {
        delete(Routes.DELETE_ROUTE) {

            val id = call.parameters["id"]!!.toInt()

            sellerCategoryRepository.deleteSellerCategory(id).let { scResponse ->
                when(scResponse) {
                    is ServiceResult.Success -> {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = scResponse.data
                        )
                    }
                    is ServiceResult.Error -> {
                        call.respond(
                            status = HttpStatusCode.BadRequest,
                            message = scResponse.errorCode.message
                        )
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
                        call.respond(
                            status = HttpStatusCode.BadRequest,
                            message = scResponse.errorCode.message
                        )
                    }
                }
            }
        }
    }
}