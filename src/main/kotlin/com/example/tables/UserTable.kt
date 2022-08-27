package com.example.tables

import com.example.models.UserSex
import com.example.models.UserType
import com.example.utils.toDatabaseString
import org.jetbrains.exposed.sql.Table
import java.time.LocalDateTime

/**
 * جدول کاربر دیتابیسمون
 * که خب مشخصه دیگه
 */
object UserTable: Table() {
    val id = long("id").uniqueIndex()
    val email = varchar(name = "email", length = 50).uniqueIndex()
    val password = varchar(name = "password", length = 500)
    val userType = enumeration<UserType>(name = "user_type")
    val dateCreated = varchar(name = "date_created", length = 50).default(LocalDateTime.now().toDatabaseString())

    override val primaryKey = PrimaryKey(id, name = "PK_USER_ID")
}

object SellerTable: Table() {
    val id = long("id").uniqueIndex()
    val userId = long("user_id") references UserTable.id
    val title = varchar(name = "title", length = 50).uniqueIndex()
    val description = varchar(name = "description", length = 500).nullable()
    val logo = varchar(name = "logo", length = 500).nullable()
    val banner = varchar(name = "banner", length = 500).nullable()
    val stateId = integer("state_id") references StateTable.id
    val cityId = integer("city_id") references CityTable.id
    val locationId = long("location_id") references LocationTable.id
    val sellerCategoryId = integer("seller_category_id") references SellerCategoryTable.id
    val resultCategoryId = integer("result_category_id") references ResultCategoryTable.id
    val foodCategoryId = integer("food_category_id") references FoodCategoryTable.id
    val deliveryFee = long("delivery_fee").nullable()
    val deliveryDuration = integer("delivery_duration").nullable()
    val phoneNumber = varchar(name = "phone_number", length = 15).nullable()
    val dateCreated = varchar(name = "date_created", length = 50).default(LocalDateTime.now().toDatabaseString())

    override val primaryKey = PrimaryKey(id, name = "PK_SELLER_ID")
}

object CustomerTable: Table() {
    val id = long("id").uniqueIndex()
    val userId = long("user_id") references UserTable.id
    val firstName = varchar(name = "first_name", length = 50)
    val lastName = varchar(name = "last_name", length = 50)
    val picture = varchar(name = "picture", length = 500)
    val phoneNumber = long("phone_number")
    val email = varchar(name = "email", length = 255)
    val address = varchar(name = "address", length = 500)
    val sex = enumeration<UserSex>("sex")
    val birthDate = varchar(name = "birth_date", length = 255)

    override val primaryKey = PrimaryKey(id, name = "PK_CUSTOMER_ID")
}