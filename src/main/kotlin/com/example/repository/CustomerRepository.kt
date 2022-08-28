package com.example.repository

import com.example.models.Customer
import com.example.models.responses.CustomerResponse
import com.example.utils.ServiceResult

interface CustomerRepository {

    suspend fun insertCustomer(customer: Customer): ServiceResult<Customer>

    suspend fun getCustomers(): ServiceResult<List<CustomerResponse?>>
    suspend fun getCustomerById(customerId: Long): ServiceResult<CustomerResponse>
    suspend fun getCustomerByEmail(email: String?): ServiceResult<CustomerResponse?>

    suspend fun updateCustomerById(customerId: Long, customer: Customer): ServiceResult<Customer>

    suspend fun deleteCustomers(): ServiceResult<List<CustomerResponse?>>
    suspend fun deleteCustomerById(customerId: Long): ServiceResult<List<CustomerResponse?>>
}