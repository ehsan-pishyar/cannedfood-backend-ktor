package com.example.routings.customer

import com.example.repository.CustomerRepository
import com.example.utils.Routes
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getCustomers(customerRepository: CustomerRepository) {
    route(Routes.CUSTOMERS_ROUTE) {
        get("/") {

            val params = call.request.rawQueryParameters
            val id = params["id"]?.toLong()
            val email = params["email"]

            if (params.isEmpty()) {
                customerRepository.getCustomers().let { customerResponse ->
                    when(customerResponse) {
                        is ServiceResult.Success -> {
                            call.respond(
                                status = HttpStatusCode.OK,
                                message = customerResponse.data
                            )
                        }
                        is ServiceResult.Error -> {
                            call.respond(
                                status = HttpStatusCode.BadRequest,
                                message = customerResponse.errorCode.message
                            )
                        }
                    }
                }
            }

            id?.let { customerId ->
                customerRepository.getCustomerById(customerId).let { customerDetails ->
                    when(customerDetails) {
                        is ServiceResult.Success -> {
                            call.respond(
                                status = HttpStatusCode.OK,
                                message = customerDetails.data
                            )
                        }
                        is ServiceResult.Error -> {
                            call.respond(
                                status = HttpStatusCode.BadRequest,
                                message = customerDetails.errorCode.message
                            )
                        }
                    }
                }
            }

            email?.let { customerEmail ->
                customerRepository.getCustomerByEmail(customerEmail).let { customerResponse ->
                    when(customerResponse) {
                        is ServiceResult.Success -> {
                            call.respond(
                                status = HttpStatusCode.OK,
                                message = customerResponse.data!!
                            )
                        }
                        is ServiceResult.Error -> {
                            call.respond(
                                status = HttpStatusCode.BadRequest,
                                message = customerResponse.errorCode.message
                            )
                        }
                    }
                }
            }
        }
    }
}