package com.example.routings.customer

import com.example.repository.CustomerRepository
import com.example.utils.Routes
import com.example.utils.ServiceResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteCustomers(customerRepository: CustomerRepository) {
    route(Routes.CUSTOMERS_ROUTE) {
        delete(Routes.DELETE_ROUTE) {

            val id = call.parameters["id"]!!.toLong()

            customerRepository.deleteCustomerById(id).let { customerResponse ->
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

        delete("/delete/") {

            customerRepository.deleteCustomers().let { customerResponse ->
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
    }
}