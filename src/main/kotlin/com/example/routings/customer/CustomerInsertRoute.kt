package com.example.routings.customer

import com.example.models.Customer
import com.example.usecases.InsertCustomerUseCase
import com.example.utils.ErrorCode
import com.example.utils.Routes
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.insertNewCustomer(insertCustomerUseCase: InsertCustomerUseCase) {
    route(Routes.CUSTOMERS_ROUTE){
        post(Routes.CREATE_ROUTE) {

            val request = call.receiveOrNull<Customer>() ?: kotlin.run {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = ErrorCode.JSON_ERROR
                )
                return@post
            }

            insertCustomerUseCase.invoke(request).let {
                call.respond(
                    status = HttpStatusCode.OK,
                    message = it
                )
            }
        }
    }
}