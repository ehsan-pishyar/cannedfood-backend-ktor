package com.example.routings

import com.example.repository.ResultsRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.resultsRoutes(resultsRepository: ResultsRepository) {
    routing {
        route("/results") {

            get("/all") {
                val results = resultsRepository.getResults()
                call.respond(results)
            }
            get("/[id]") {
                val resultId = call.parameters["id"]?.toInt()
                val result = resultsRepository.getResultById(resultId!!)
                call.respond(result!!)
            }
            get("/[title]") {
                val resultTitle = call.parameters["title"]
                val results = resultsRepository.getResultsByTitle(resultTitle!!)
                call.respond(results)
            }
            get("/[description]") {
                val description = call.parameters["description"]
                val results = resultsRepository.getResultsByDescription(description!!)
                call.respond(results)
            }
            get("/[id]") {
                val typeId = call.parameters["id"]?.toInt()
                val result = resultsRepository.getResultsByTypeId(typeId!!)
                call.respond(result)
            }
            get("/[title]") {
                val typeTitle = call.parameters["title"]
                val results = resultsRepository.getResultsByTypeTitle(typeTitle!!)
                call.respond(results)
            }
            get("/[id]") {
                val categoryId = call.parameters["id"]?.toInt()
                val result = resultsRepository.getResultsByCategoryId(categoryId!!)
                call.respond(result)
            }
            get("/[title]") {
                val categoryTitle = call.parameters["title"]
                val results = resultsRepository.getResultsByCategoryTitle(categoryTitle!!)
                call.respond(results)
            }
            get("/[price]") {
                val price = call.parameters["price"]?.toInt()
                val results = resultsRepository.getResultsByPrice(price!!)
                call.respond(results)
            }
            get("/[rating]") {
                val rating = call.parameters["rating"]?.toDouble()
                val results = resultsRepository.getResultsByRating(rating!!)
                call.respond(results)
            }
            get("/[count]") {
                val voteCount = call.parameters["count"]?.toInt()
                val results = resultsRepository.getResultsByVoteCount(voteCount!!)
                call.respond(results)
            }
            get("/[discount]") {
                val discount = call.parameters["discount"]?.toInt()
                val results = resultsRepository.getResultsByDiscount(discount!!)
                call.respond(results)
            }
            get("/[date]") {
                val date = call.parameters["date"]
                val results = resultsRepository.getResultsByDateAdded(date!!)
                call.respond(results)
            }
            get("/[duration]") {
                val duration = call.parameters["duration"]?.toInt()
                val results = resultsRepository.getResultsByPrepareDuration(duration!!)
                call.respond(results)
            }
        }
    }
}