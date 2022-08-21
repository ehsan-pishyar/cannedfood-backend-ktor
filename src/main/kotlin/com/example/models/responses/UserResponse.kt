package com.example.models.responses

import com.example.models.ResponseErrors
import com.example.models.Results
import com.example.models.User
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val user: User?,
    val errors: List<ResponseErrors> = emptyList()
)

@Serializable
data class SellerResponse(
    val title: String = "",
    val description: String = "",
    val logo: String = "",
    val banner: String = "",
    val state: String = "",
    val city: String = "",
    val location: String = "",
    val results: List<Results> = emptyList(),
    val seller_rating: Double = 0.0,
    val vote_count: Long = 0L,
    val delivery_fee: Long = 0L,
    val delivery_duration: Int = 0,
    val phone_number: String = ""
)
