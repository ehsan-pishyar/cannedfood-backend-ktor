package com.example.models.responses

import com.example.models.FoodCategory
import com.example.models.Seller
import kotlinx.serialization.Serializable

@Serializable
data class ResultResponse(
    val id: Long?,
    val title: String?,
    val description: String?,
    val image_path: String?,
    val price: Long?,
    val discount: Int?,
    val prepare_duration: Int?,
    val seller: String?,
    val food_category: String? = null,
    val date_created: String?
)

@Serializable
data class ResultDetailsResponse(
    val id: Long?,
    val title: String?,
    val description: String?,
    val image_path: String?,
    val price: Long?,
    val discount: Int?,
    val prepare_duration: Int?,
    val seller: Seller?,
    val food_category: FoodCategory?,
    val date_created: String?
)