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
    val id = integer("id").uniqueIndex().autoIncrement()
    val email = varchar(name = "email", length = 50).uniqueIndex()
    val password = varchar(name = "password", length = 500)
    val userType = enumeration<UserType>(name = "user_type")
    val dateCreated = varchar(name = "date_created", length = 50).default(LocalDateTime.now().toDatabaseString())

    override val primaryKey = PrimaryKey(id, name = "PK_USER_ID")
}

object SellerTable: Table() {
    val id = integer("id").uniqueIndex().autoIncrement()
    val userId = integer("user_id") references UserTable.id
    val title = varchar(name = "title", length = 50).uniqueIndex()
    val description = varchar(name = "description", length = 500)
    val logo = varchar(name = "logo", length = 500)
    val banner = varchar(name = "banner", length = 500)
    val isOpen = bool("is_open").default(false)
    val rating = double("rating").default(0.0)
    val voteCount = integer("vote_count").default(0)
    val deliveryFee = integer("delivery_fee").default(0)
    val deliveryDuration = integer("delivery_duration").default(0)

    override val primaryKey = PrimaryKey(id, name = "PK_SELLER_ID")
}

object CustomerTable: Table() {

    val id = integer("id").uniqueIndex().autoIncrement()
    val userId = integer("user_id") references UserTable.id
    val firstName = varchar(name = "first_name", length = 50)
    val lastName = varchar(name = "last_name", length = 50)
    val avatar = varchar(name = "avatar", length = 500)
    val phoneNumber = long("phone_number")
    val email = varchar(name = "email", length = 255)
    val address = varchar(name = "address", length = 500)
    val sex = enumeration<UserSex>("sex")
    val birthDate = varchar(name = "birth_date", length = 255)

    override val primaryKey = PrimaryKey(id, name = "PK_CUSTOMER_ID")
}