package com.example.routings

import com.example.models.Category
import com.example.repository.CategoryRepository
import com.example.tables.CategoryTable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.plus

fun Application.categoryRoutes(categoryRepository: CategoryRepository) {
    routing {
        route("/categories") {

            post("/category") {
                val requestBody = call.receive<Category>()
                val categorySize = categoryRepository.getCategories().size
                val category = requestBody.copy(id = categorySize.plus(1))
                val result = categoryRepository.insertCategory(category)
                call.respond(result!!)
            }

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