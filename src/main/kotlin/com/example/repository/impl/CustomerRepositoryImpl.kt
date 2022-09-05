package com.example.repository.impl

import com.example.models.Customer
import com.example.models.User
import com.example.models.responses.CustomerDetailsResponse
import com.example.models.responses.CustomerResponse
import com.example.models.responses.LocationResponse
import com.example.repository.CustomerRepository
import com.example.tables.*
import com.example.tables.CustomerTable.locationId
import com.example.tables.DatabaseFactory.dbQuery
import com.example.utils.ErrorCode
import com.example.utils.ServiceResult
import com.example.utils.randomIdGenerator
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class CustomerRepositoryImpl : CustomerRepository {

    override suspend fun insertCustomer(customer: Customer): ServiceResult<Customer> {
        return try {
            dbQuery {
                CustomerTable.insert {
                    it[id] = randomIdGenerator()
                    it[userId] = customer.user_id
                    it[firstName] = customer.first_name
                    it[lastName] = customer.last_name
                    it[picture] = customer.picture
                    it[phoneNumber] = customer.phone_number
                    it[locationId] = customer.location_id
                    it[sex] = customer.sex
                    it[birthDate] = customer.birth_date
                }
                    .resultedValues?.single().let {
                        ServiceResult.Success(rowToCustomer(it)!!)
                    }
            }
        } catch (e: Exception) {
            println(e)
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getCustomers(): ServiceResult<List<CustomerResponse?>> {
        return try {
            dbQuery {
                CustomerTable
                    .innerJoin(UserTable, {userId}, {id})
                    .innerJoin(LocationTable, {locationId}, {id})
//                (CustomerTable innerJoin UserTable innerJoin LocationTable)
                    .selectAll()
                    .orderBy(CustomerTable.dateCreated to SortOrder.DESC)
                    .map { rowToCustomerResponse(it) }
            }.let {
                ServiceResult.Success(it)
            }
        } catch (e: Exception) {
            println(e)
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    /**
     * خوب قراره یه مشتری رو بر اساس شناسه پیدا کنیم، یعنی جزئیات مشتری رو داشته باشیم. این مشتری قراره به جدول های دیگه هم رفرنس داشته باشه.
     * مثلا ایمیل این مشتری چیه که این ایمیل توی جدول کاربر هستش که مشتری رو به اون کاربر اختصاص یا رفرنس دادیم.
     * و اینکه لوکیشن این مشتری کجاست که بر اساس اون لوکیشنی که مشتری رو بهش اختصاص دادیم میاد از جدول لوکیشن اون مکان، شهر و استان مشتری رو برامون بر میگردونه.
     * چون قراره توی آبجکت مشتری، آبجکت لوکیشن رو هم برگردونه، واسه همین باید یه ترنزکشن هم به آبجکت لوکیشن بزنیم تا لوکیشن رو برگردونیم.
     */
    override suspend fun getCustomerById(customerId: Long): ServiceResult<CustomerDetailsResponse> {
        lateinit var customerDetailsResponse: CustomerDetailsResponse
        return try {
            dbQuery {
                val customer = selectCustomer(customerId)
                val location = selectLocation(customer!!.location_id)

                val user = transaction {
                    UserTable.select {
                        (UserTable.id eq customer.user_id)
                    }
                        .map { rowToUser(it) }
                        .single()
                }
                customerDetailsResponse = CustomerDetailsResponse(
                    id = customer.id,
                    first_name = customer.first_name,
                    last_name = customer.last_name,
                    email = user?.email,
                    picture = customer.picture,
                    sex = customer.sex.toString(),
                    location = location,
                    date_created = customer.date_created
                )
            }
            ServiceResult.Success(customerDetailsResponse)
        } catch (e: Exception) {
            println(e)
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    // TODO: 8/31/2022 Fix this shit
    override suspend fun getCustomerByEmail(email: String?): ServiceResult<CustomerResponse?> {
        lateinit var customerResponse: CustomerResponse
        return try {
            dbQuery {
                val user = transaction {
                    UserTable.select {
                        (UserTable.email like "$email%")
                    }
                        .map { rowToUser(it) }
                        .singleOrNull()
                }

                val customer = selectCustomer(user!!.id)
                val location = selectLocation(customer!!.location_id)

                customerResponse = CustomerResponse(
                    id = customer.id,
                    first_name = customer.first_name,
                    last_name = customer.last_name,
                    email = user.email,
                    picture = customer.picture,
                    sex = customer.sex.toString(),
                    location = location?.title,
                    date_created = customer.date_created
                )
            }
            ServiceResult.Success(customerResponse)
        } catch (e: Exception) {
            println(e)
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun updateCustomerById(customerId: Long, customer: Customer): ServiceResult<Customer> {
        return try {
            dbQuery {
                CustomerTable.update({
                    CustomerTable.id eq customerId
                }) {
                    it[id] = customerId
                    it[firstName] = customer.first_name
                    it[lastName] = customer.last_name
                    it[picture] = customer.picture
                    it[phoneNumber] = customer.phone_number
                    it[locationId] = customer.location_id
                    it[sex] = customer.sex
                    it[birthDate] = customer.birth_date
                }

                selectCustomer(customerId).let {
                    ServiceResult.Success(it!!)
                }
            }
        } catch (e: Exception) {
            println(e)
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun deleteCustomers(): ServiceResult<List<CustomerResponse?>> {
        return try {
            dbQuery {
                CustomerTable.deleteAll()

                getCustomersResponse().let {
                    ServiceResult.Success(it)
                }
            }
        } catch (e: Exception) {
            println(e)
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun deleteCustomerById(customerId: Long): ServiceResult<List<CustomerResponse?>> {
        return try {
            dbQuery {
                CustomerTable.deleteWhere {
                    (CustomerTable.id eq customerId)
                }

                getCustomersResponse().let {
                    ServiceResult.Success(it)
                }
            }
        } catch (e: Exception) {
            println(e)
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    private fun rowToCustomer(row: ResultRow?): Customer? {
        if (row == null) return null

        return Customer(
            id = row[CustomerTable.id],
            user_id = row[CustomerTable.userId],
            first_name = row[CustomerTable.firstName],
            last_name = row[CustomerTable.lastName],
            picture = row[CustomerTable.picture]!!,
            phone_number = row[CustomerTable.phoneNumber],
            location_id = row[locationId],
            sex = row[CustomerTable.sex]!!,
            birth_date = row[CustomerTable.birthDate]!!,
            date_created = row[CustomerTable.dateCreated]
        )
    }

    private fun rowToCustomerResponse(row: ResultRow?): CustomerResponse? {
        if (row == null) return null

        return CustomerResponse(
            id = row[CustomerTable.id],
            first_name = row[CustomerTable.firstName],
            last_name = row[CustomerTable.lastName],
            email = row[UserTable.email],
            picture = row[CustomerTable.picture],
            sex = row[CustomerTable.sex].toString(),
            location = row[LocationTable.title],
            date_created = row[CustomerTable.dateCreated]
        )
    }

    private fun rowToLocationResponse(row: ResultRow?): LocationResponse? {
        if (row == null) return null

        return LocationResponse(
            id = row[LocationTable.id],
            title = row[LocationTable.title],
            lat = row[LocationTable.lat],
            lon = row[LocationTable.lon],
            city = row[CityTable.title],
            state = row[StateTable.title]
        )
    }

    private fun rowToUser(row: ResultRow?): User? {
        if (row == null) return null

        return User(
            email = row[UserTable.email]
        )
    }

    private fun selectLocation(locationId: Long): LocationResponse? {
        return transaction {
            (LocationTable innerJoin CityTable innerJoin StateTable).select {
                (LocationTable.id eq locationId)
            }
                .map { rowToLocationResponse(it) }
                .singleOrNull()
        }
    }

    private fun selectCustomer(id: Long): Customer?{
        return transaction {
            CustomerTable.select {
                (CustomerTable.userId eq id)
            }
                .map { rowToCustomer(it) }
                .singleOrNull()
        }
    }

    private fun getCustomersResponse(): List<CustomerResponse?> {
        return transaction {
            (CustomerTable innerJoin UserTable innerJoin LocationTable).selectAll()
                .orderBy(CustomerTable.dateCreated to SortOrder.DESC)
                .map { rowToCustomerResponse(it) }
        }
    }
}