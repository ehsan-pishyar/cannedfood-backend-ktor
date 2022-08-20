package com.example.routings

import com.example.models.Results
import com.example.repository.ResultsRepository
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.resultsRoutes(resultsRepository: ResultsRepository) {
    routing {
        route("/results") {

            post("/result") {
                val result = call.receive<Results>()
                resultsRepository.insertResult(result)
            }

            get("/") {
                val params = call.request.rawQueryParameters
                val id = params["result_id"]?.toInt()
                val title = params["title"]
                val scId = params["description"]?.toInt()
                val rcId = params["result_id"]?.toInt()
                val fcId = params["result_id"]?.toInt()
                val price = params["price"]?.toInt()
                val rating = params["rating"]?.toDouble()
                val voteCount = params["vote_count"]?.toInt()
                val discount = params["discount"]?.toInt()
                val dateAdded = params["date_added"]
                val pd = params["prepare_duration"]?.toInt()

                if (id != null) {
                    val result = resultsRepository.getResultById(id)
                    call.respond(result!!)
                } else if (title != null) {
                    val results = resultsRepository.getResultsByTitle(title)
                    call.respond(results)
                } else if (scId != null) {
                    val results = resultsRepository.getResultsBySellerCategoryId(scId)
                    call.respond(results)
                } else if (rcId != null) {
                    val results = resultsRepository.getResultsByResultCategoryId(rcId)
                    call.respond(results)
                } else if (fcId != null) {
                    val results = resultsRepository.getResultsByFoodCategoryId(fcId)
                    call.respond(results)
                } else if (price != null) {
                    val results = resultsRepository.getResultsByPrice(price)
                    call.respond(results)
                } else if (rating != null) {
                    val results = resultsRepository.getResultsByRating(rating)
                    call.respond(results)
                } else if (voteCount != null) {
                    val results = resultsRepository.getResultsByVoteCount(voteCount)
                    call.respond(results)
                } else if (discount != null) {
                    val results = resultsRepository.getResultsByDiscount(discount)
                    call.respond(results)
                } else if (dateAdded != null) {
                    val results = resultsRepository.getResultsByDateAdded(dateAdded)
                    call.respond(results)
                } else if (pd != null) {
                    val results = resultsRepository.getResultsByPrepareDuration(pd)
                    call.respond(results)
                }
            }

            put("/result") {
                val result = call.receive<Results>()
                resultsRepository.updateResult(result.result_id, result)
            }

            delete("/result") {
                val params = call.request.rawQueryParameters
                val id = params["result_id"]?.toInt()

                if (id != null) {
                    resultsRepository.deleteResultById(id)
                } else {
                    resultsRepository.deleteResults()
                }
            }
        }
    }
}