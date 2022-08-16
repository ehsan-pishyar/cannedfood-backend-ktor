package com.example.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val email: String,
    val password: String,
    val user_type: UserType = UserType.DEFAULT,
    val date_created: String,
)

enum class UserType(val value: Int) {
    DEFAULT(0),

    @SerialName("Admin")
    ADMIN(1),

    @SerialName("Seller")
    SELLER(2),

    @SerialName("Customer")
    CUSTOMER(3)
}


