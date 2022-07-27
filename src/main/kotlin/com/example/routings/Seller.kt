package com.example.routings

import com.example.repository.SellerRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.sellerRoutes(sellerRepository: SellerRepository) {
    routing {
        route("/sellers") {

            get("/") {
                val sellers = sellerRepository.getSellers()
                call.respond(sellers)
            }
            get("/[id]") {
                val sellerId = call.parameters["id"]?.toInt()
                val seller = sellerRepository.getSellerById(sellerId!!)
                call.respond(seller!!)
            }
            get("/[title]") {
                val sellerTitle = call.parameters["title"]
                val seller = sellerRepository.getSellerByTitle(sellerTitle!!)
                call.respond(seller!!)
            }
            get("/[slice]") {
                val sellerTitleSlice = call.parameters["slice"]
                val sellers = sellerRepository.getSellersByTitleSlice(sellerTitleSlice!!)
                call.respond(sellers)
            }
            get("/[description]") {
                val sellerDescription = call.parameters["description"]
                val sellers = sellerRepository.getSellersByDescription(sellerDescription!!)
                call.respond(sellers)
            }
            get("/[id]") {
                val stateId = call.parameters["id"]?.toInt()
                val sellers = sellerRepository.getSellersByStateId(stateId!!)
                call.respond(sellers)
            }
            get("/[title]") {
                val stateTitle = call.parameters["title"]
                val sellers = sellerRepository.getSellersByStateTitle(stateTitle!!)
                call.respond(sellers)
            }
            get("/[id]") {
                val cityId = call.parameters["id"]?.toInt()
                val sellers = sellerRepository.getSellersByCityId(cityId!!)
                call.respond(sellers)
            }
            get("/[title]") {
                val cityTitle = call.parameters["title"]
                val sellers = sellerRepository.getSellersByCityTitle(cityTitle!!)
                call.respond(sellers)
            }
            get("/[location]") {
                val location = call.parameters["location"]
                val sellers = sellerRepository.getSellersByLocationTitle(location!!)
                call.respond(sellers)
            }
            get("/[id]") {
                val resultId = call.parameters["id"]?.toInt()
                val sellers = sellerRepository.getSellersByResultsId(resultId!!)
                call.respond(sellers)
            }
            get("/[title]") {
                val resultTitle = call.parameters["title"]
                val sellers = sellerRepository.getSellersByResultsTitle(resultTitle!!)
                call.respond(sellers)
            }
            get("/[status]") {
                val status = call.parameters["status"]?.toBoolean()
                val sellers = sellerRepository.getSellersByOpenStatus(status!!)
                call.respond(sellers)
            }
            get("/[duration]") {
                val deliveryDuration = call.parameters["duration"]?.toInt()
                val sellers = sellerRepository.getSellersByDeliveryDuration(deliveryDuration!!)
                call.respond(sellers)
            }
            get("/[fee]") {
                val deliveryFee = call.parameters["fee"]?.toInt()
                val sellers = sellerRepository.getSellersByDeliveryFee(deliveryFee!!)
                call.respond(sellers)
            }
        }
    }
}