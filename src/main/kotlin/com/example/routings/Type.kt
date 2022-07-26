package com.example.routings

import com.example.repository.TypeRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.typeRoutes(typeRepository: TypeRepository) {
    routing {
        route("/type") {

            get("/all") {
                val types = typeRepository.getTypes()
                call.respond(types)
            }
            get("/[id]") {
                val typeId = call.parameters["id"]?.toInt()
                val type = typeRepository.getTypeById(typeId!!)
                call.respond(type!!)
            }
            get("/[title]") {
                val typeTitle = call.parameters["name"]
                val type = typeRepository.getTypeByTitle(typeTitle!!)
                call.respond(type!!)
            }
        }
    }
}