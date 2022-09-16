package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class SellerOpenHours(
    val id: Long = 0L,
    val seller_id: Long = 0L,
    val saturday: Int = 0,
    val sunday: Int = 0,
    val monday: Int = 0,
    val tuesday: Int = 0,
    val wednesday: Int = 0,
    val thursday: Int = 0,
    val friday: Int = 0
)

@Serializable
data class SellerCloseHours(
    val id: Long = 0L,
    val seller_id: Long = 0L,
    val saturday: Int = 0,
    val sunday: Int = 0,
    val monday: Int = 0,
    val tuesday: Int = 0,
    val wednesday: Int = 0,
    val thursday: Int = 0,
    val friday: Int = 0
)