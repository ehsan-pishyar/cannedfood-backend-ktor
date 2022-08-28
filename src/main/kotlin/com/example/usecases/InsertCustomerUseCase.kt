package com.example.usecases

import com.example.models.Customer
import com.example.repository.CustomerRepository
import com.example.utils.ServiceResult
import com.example.utils.toDatabaseString
import java.time.LocalDateTime

class InsertCustomerUseCase(
    private val customerRepository: CustomerRepository
) {

    suspend operator fun invoke(customer: Customer): Customer? {
        customerRepository.insertCustomer(customer.copy(
            date_created = LocalDateTime.now().toDatabaseString()
        )).let {
            return when(it){
                is ServiceResult.Success -> it.data
                else -> null
            }
        }
    }
}