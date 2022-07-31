package com.example.routings

import com.example.repository.FoodCategoryRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.typeRoutes(foodCategoryRepository: FoodCategoryRepository) {
    routing {
        route("/types") {

            get("/") {
                val types = foodCategoryRepository.getTypes()
                call.respond(types)
            }
            get("/[id]") {
                val typeId = call.parameters["id"]?.toInt()
                val type = foodCategoryRepository.getTypeById(typeId!!)
                call.respond(type!!)
            }
            get("/[title]") {
                val typeTitle = call.parameters["name"]
                val type = foodCategoryRepository.getTypeByTitle(typeTitle!!)
                call.respond(type!!)
            }
        }
    }
}