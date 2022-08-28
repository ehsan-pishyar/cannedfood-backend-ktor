package com.example.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * ساختار کاربر فقط برای ثبت نام
 * برای ذخیره داده ها استفاده میشه و البته نمایش اونها
 */
@Serializable
data class User(
    val id: Long = 0L,
    val email: String = "",
    val password: String = "",
    val user_type: UserType = UserType.TEST,    // Admin, Seller, Customer
    val date_created: String = "",
)

@Serializable
data class Seller(
    val id: Long = 0L,
    val user_id: Long = 0,                  // This seller belongs to user {user_id}
    val title: String = "",
    val description: String = "",
    val logo: String = "",                  // Logo image path
    val banner: String = "",
    val state_id: Int = 0,                  // Belongs to State {state_id}
    val city_id: Int = 0,                   // Belongs to city {city_id}
    val location_id: Long = 0L,             // Belongs to location {location_id}
    val seller_category_id: Int = 0,
    val result_category_id: Int = 0,
    val food_category_id: Int = 0,
    val delivery_fee: Long = 0L,
    val delivery_duration: Int = 0,
    val phone_number: String = "",
    val date_created: String = ""
)

@Serializable
data class Customer(
    val id: Long = 0L,
    val user_id: Long = 0L,                  // This seller belongs to user {user_id}
    val first_name: String = "",
    val last_name: String = "",
    val email: String = "",
    val picture: String = "",               // User picture image path
    val phone_number: Long = 0L,
    val location_id: Long = 0L,
    val sex: UserSex = UserSex.UNKNOWN,     // Male, Female
    val birth_date: String = "",
    val date_created: String = ""
)


/**
 * برای تعیین نوع کاربری که قراره ثبت نام کنه
 */
@Serializable
enum class UserType(val value: Int) {
    TEST(0),

    @SerialName("Admin")
    ADMIN(1),

    @SerialName("Seller")
    SELLER(2),

    @SerialName("Customer")
    CUSTOMER(3)
}

@Serializable
enum class UserSex(val value: Int) {
    UNKNOWN(0),

    @SerialName("Man")
    MAN(1),

    @SerialName("Woman")
    WOMAN(2)
}