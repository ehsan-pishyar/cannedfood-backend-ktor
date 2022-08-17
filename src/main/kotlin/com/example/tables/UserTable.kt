package com.example.tables

import com.example.models.UserType
import com.example.utils.toDatabaseString
import org.jetbrains.exposed.sql.Table
import java.time.LocalDateTime

/**
 * جدول کاربر دیتابیسمون
 * که خب مشخصه دیگه
 */
object UserTable: Table() {

    val userId = integer("user_id").uniqueIndex().autoIncrement()
    val email = varchar(name = "email", length = 50).uniqueIndex()
    val password = varchar(name = "password", length = 500)
    val userType = enumeration<UserType>(name = "user_type")
    val dateCreated = varchar(name = "date_created", length = 50).default(LocalDateTime.now().toDatabaseString())

    override val primaryKey = PrimaryKey(userId, name = "PK_USER_ID")
}

object SellerTable: Table() {

    val sellerId = integer("seller_id").uniqueIndex().autoIncrement()
    val userId = integer("user_id") references UserTable.userId
    val title = varchar(name = "title", length = 50)
    val description = varchar(name = "description", length = 500)
    val logo = varchar(name = "logo", length = 500)
    val banner = varchar(name = "banner", length = 500)
    val sellerCategoryId = integer("seller_category_id") references SellerCategoryTable.scId
    val resultCategoryId = integer(name = "result_category_id") references ResultCategoryTable.rcId
    val foodCategoryId = integer(name = "food_category_id") references FoodCategoryTable.fcId
    val stateId = integer("stateId") references StateTable.id
    val cityId = integer("cityId") references CityTable.id
    val location = varchar(name = "location", length = 100)
    val resultsId = integer("results_id") references ResultsTable.id
    val isOpen = bool("is_open")
    val rating = double("rating")
    val voteCount = integer("vote_count")
    val deliveryFee = integer("delivery_fee")
    val deliveryDuration = integer("delivery_duration")

    override val primaryKey = PrimaryKey(sellerId, name = "PK_SELLER_ID")
}

object CustomerTable: Table() {

    val id = integer("id").uniqueIndex().autoIncrement()
    val title = varchar(name = "title", length = 50)
    val description = varchar(name = "description", length = 500)
    val logo = varchar(name = "logo", length = 500)
    val banner = varchar(name = "banner", length = 500)
    val sellerCategoryId = integer("seller_category_id") references SellerCategoryTable.scId
    val resultCategoryId = integer(name = "result_category_id") references ResultCategoryTable.rcId
    val foodCategoryId = integer(name = "food_category_id") references FoodCategoryTable.fcId
    val stateId = integer("stateId") references StateTable.id
    val cityId = integer("cityId") references CityTable.id
    val location = varchar(name = "location", length = 100)
    val resultsId = integer("results_id") references ResultsTable.id
    val isOpen = bool("is_open")
    val rating = double("rating")
    val voteCount = integer("vote_count")
    val deliveryFee = integer("delivery_fee")
    val deliveryDuration = integer("delivery_duration")
    val userId = integer("user_id") references UserTable.userId

    override val primaryKey = PrimaryKey(id, name = "PK_SELLER_ID")
}