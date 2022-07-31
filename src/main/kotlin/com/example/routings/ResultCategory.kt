package com.example.routings

import com.example.models.ResultCategory
import com.example.repository.ResultCategoryRepository
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.categoryRoutes(resultCategoryRepository: ResultCategoryRepository) {
    routing {
        route("/result-categories") {

//            post("/category") {
//                val requestBody = call.receive<ResultCategory>()
//                val categorySize = resultCategoryRepository.getCategories().size
//                val category = requestBody.copy(id = categorySize.plus(1))
//                val result = resultCategoryRepository.insertCategory(category)
//                call.respond(result!!)
//            }

            // sc = seller category & rc = result category
            get("/") {
                val params = call.request.rawQueryParameters
                val sellerCategoryId = params["sc_id"]?.toInt()
                val resultCategoryTitle = params["rc_title"]

                if (resultCategoryTitle == null) {
                    val resultCategories = resultCategoryRepository.getResultCategories(sellerCategoryId!!)
                    call.respond(resultCategories)
                } else {
                    val resultCategories =
                        resultCategoryRepository.getResultCategoriesByTitle(sellerCategoryId!!, resultCategoryTitle)
                    call.respond(resultCategories)
                }
            }
        }
    }
}