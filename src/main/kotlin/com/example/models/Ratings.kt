package com.example.models

import com.example.utils.randomIdGenerator
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SellerRating(
    val id: Long = randomIdGenerator(),
    val from_customer_id: Long = 0L,
    val rating: Int = 0,
)

@Serializable
data class ResultRating(
    val id: Long = randomIdGenerator(),
    val from_customer_id: Long = 0,
    val rating: Int = 0,
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