package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class SellerOpenStatus(
    val id: Int = 0,
    val seller_id: Int = 0,
    val is_open: Boolean = false
)