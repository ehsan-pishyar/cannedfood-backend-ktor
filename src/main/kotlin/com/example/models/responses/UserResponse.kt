package com.example.models.responses

import com.example.models.ResponseErrors
import com.example.models.Results
import com.example.models.SellerComment
import com.example.models.User
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val user: User?,
    val errors: List<ResponseErrors> = emptyList()
)

@Serializable
data class SellerResponse(
    val id: Long?,
    val title: String?,
    val description: String?,
    val logo: String?,
    val banner: String?,
    val location: LocationResponse? = null,
    val results: List<Results?> = emptyList(),
    val rating: Double?,
    val vote_count: Long?,
    val comments: List<SellerComment?> = emptyList(),
    val delivery_fee: Long?,
    val delivery_duration: Int?,
    val phone_number: String?
)

@Serializable
data class CustomerResponse(
    val id: Long?
)