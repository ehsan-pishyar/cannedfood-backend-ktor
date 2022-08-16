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

            post("/result-category") {
                val rc = call.receive<ResultCategory>()
                resultCategoryRepository.insertResultCategory(rc)
            }

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
                        resultCategoryRepository.getResultCategoriesByTitle(
                            resultCategoryTitle
                        )
                    call.respond(resultCategories)
                }
            }

            put("/result-category") {
                val rc = call.receive<ResultCategory>()
                resultCategoryRepository.updateResultCategory(
                    rc.id,
                    rc
                )
            }

            delete("/result-category") {
                val params = call.request.rawQueryParameters
                val scId = params["sc_id"]?.toInt()
                val rcId = params["rc_id"]?.toInt()

                if (scId != null && rcId != null) {
                    resultCategoryRepository.deleteResultCategory(scId)
                } else if (scId != null && rcId == null) {
                    resultCategoryRepository.deleteResultCategoriesOfSellerCategory(scId)
                } else if (scId == null && rcId == null) {
                    resultCategoryRepository.deleteResultCategories()
                }
            }
        }
    }
}