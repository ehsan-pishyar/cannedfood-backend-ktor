package com.example.routings

import com.example.models.Location
import com.example.repository.LocationRepository
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.locationRoutes(locationRepository: LocationRepository) {
    routing {
        route("/locations") {

            post("/location") {
                val location = call.receive<Location>()
                locationRepository.insertLocation(location)
            }

            get("/") {
                val params = call.request.rawQueryParameters
                val cityId = params["city_id"]!!.toInt()
                val locationId = params["location_id"]!!.toInt()
                val locationTitle = params["title"]

                if (locationId != null && locationTitle == null) {
                    val location = locationRepository.getLocation(locationId)
                    call.respond(location)
                } else if (cityId != null && locationId == null && locationTitle == null) {
                    val locations = locationRepository.getLocations(cityId)
                    call.respond(locations)
                } else if (locationId == null && locationTitle != null) {
                    val locations = locationRepository.getLocationsByTitle(locationTitle)
                    call.respond(locations)
                }
            }

            put("/location") {
                val location = call.receive<Location>()
                locationRepository.updateLocation(location.id, location)
            }

            delete("/location") {
                val params = call.request.rawQueryParameters
                val locationId = params["location_id"]!!.toInt()

                if (locationId != null) {
                    locationRepository.deleteLocation(locationId)
                } else {
                    locationRepository.deleteLocations()
                }
            }
        }
    }
}