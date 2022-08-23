package com.example.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SellerCategory(
    val id: Int = 0,
    val title: String = "",
    val image_path: String = ""
)

@Serializable
data class ResultCategory(
    val id: Int = 0,
    val title: String = "",
    val image_path: String = "",
    val seller_category_id: Int = 0
)

@Serializable
data class FoodCategory(
    val id: Int = 0,
    val title: String = "",
    val image_path: String = "",
    val result_category_id: Int = 0
)

@Serializable
data class SellerRating(
    val id: Int = 0,
    val from_customer_id: Int = 0,
    val rating: Double = 0.0,
    val to_seller_id: Int = 0
)

@Serializable
data class SellerComment(
    val id: Int = 0,
    val from_customer_id: Int = 0,
    val message: String = "",
    val to_seller_id: Int = 0,
)

@Serializable
data class SellerOpenStatus(
    val id: Int = 0,
    val seller_id: Int = 0,
    val is_open: Boolean = false
)

@Serializable
enum class RatingStars(val value: Int) {
    NOT_RATED(0),

    @SerialName("Awful")
    AWFUL(1),

    @SerialName("Bad")
    BAD(2),

    @SerialName("Normal")
    NORMAL(3),

    @SerialName("Good")
    GOOD(4),

    @SerialName("Nice")
    NICE(5),
}