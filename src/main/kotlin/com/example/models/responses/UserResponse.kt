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
    val id: Int?,
    val title: String?,
    val description: String?,
    val logo: String?,
    val banner: String?,
    val state: String?,
    val city: String?,
    val location: String?,
    val results: List<Results?>,
    val seller_rating: String?,
    val vote_count: Long?,
    val delivery_fee: Long?,
    val delivery_duration: Int?,
    val phone_number: String?
)
