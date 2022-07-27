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

            post("/category") {
                val requestBody = call.receive<ResultCategory>()
                val categorySize = resultCategoryRepository.getCategories().size
                val category = requestBody.copy(id = categorySize.plus(1))
                val result = resultCategoryRepository.insertCategory(category)
                call.respond(result!!)
            }

            get("/") {
                val categories = resultCategoryRepository.getCategories()
                call.respond(categories)
            }
            get("/[id]") {
                val categoryId = call.parameters["id"]?.toInt()
                val category = resultCategoryRepository.getCategoryById(categoryId!!)
                call.respond(category!!)
            }
            get("/[title]") {
                val categoryTitle = call.parameters["title"] // check if condition for null error
                val category = resultCategoryRepository.getCategoryByTitle(categoryTitle!!)
                call.respond(category!!)
            }
        }
    }
}