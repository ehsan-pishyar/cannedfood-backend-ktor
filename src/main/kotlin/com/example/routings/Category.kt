package com.example.routings

import com.example.repository.CategoryRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.categoryRoutes(categoryRepository: CategoryRepository) {
    routing {
        route("/category") {

            get("/all") {
                val categories = categoryRepository.getCategories()
                call.respond(categories)
            }
            get("/[id]") {
                val categoryId = call.parameters["id"]?.toInt()
                val category = categoryRepository.getCategoryById(categoryId!!)
                call.respond(category!!)
            }
            get("/[title]") {
                val categoryTitle = call.parameters["title"] // check if condition for null error
                val category = categoryRepository.getCategoryByTitle(categoryTitle!!)
                call.respond(category!!)
            }
        }
    }
}