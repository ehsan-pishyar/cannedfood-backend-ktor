package com.example.models

import com.example.utils.randomIdGenerator
import kotlinx.serialization.Serializable

@Serializable
data class SellerComment(
    val id: Long = randomIdGenerator(),
    val from_customer_id: Long = 0L,
    val message: String = "",
    val date_created: String = ""
)

@Serializable
data class ResultComment(
    val id: Long = randomIdGenerator(),
    val from_customer_id: Long = 0L,
    val message: String = "",
    val date_created: String = ""
)