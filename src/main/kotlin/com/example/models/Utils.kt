package com.example.models

import kotlinx.serialization.Serializable

// روزهای هفته رو بیاریم تا فروشنده ساعت های باز بودن رستوران خودشو وارد کنه
@Serializable
data class SellerOpenStatus(
    val id: Int = 0,
    val seller_id: Long = 0,
    val is_open: Boolean = false
)

@Serializable
data class SellerOpenHour(
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
data class SellerCloseHour(
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