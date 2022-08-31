package com.example.models.responses

import com.example.models.FoodCategory
import com.example.models.ResponseErrors
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
    val rating: Double? = null,
    val delivery_fee: Long?,
    val delivery_duration: Int?,
    val food_categories: List<FoodCategory?> = emptyList()
)

@Serializable
data class SellerDetailsResponse(
    val id: Long?,
    val title: String?,
    val description: String?,
    val logo: String?,
    val banner: String?,
    val location: LocationResponse? = null,
    val results: List<ResultResponse?>? = emptyList(),
//    val rating: Double?,
//    val vote_count: Long?,
    val comments: List<SellerCommentResponse?> = emptyList(),
    val delivery_fee: Long?,
    val delivery_duration: Int?,
    val phone_number: String?,
    val date_created: String?
)

@Serializable
data class CustomerResponse(
    val id: Long?,
    val first_name: String?,
    val last_name: String?,
    val email: String?,
    val picture: String?,
    val sex: String?,
    val location: String?,
    val date_created: String?
)

@Serializable
data class CustomerDetailsResponse(
    val id: Long?,
    val first_name: String?,
    val last_name: String?,
    val email: String?,
    val picture: String?,
    val sex: String?,
    val location: LocationResponse? = null,
    val date_created: String?
)