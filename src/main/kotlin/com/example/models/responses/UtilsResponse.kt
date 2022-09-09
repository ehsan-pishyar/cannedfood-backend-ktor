package com.example.models.responses

import kotlinx.serialization.Serializable

@Serializable
data class SellerOpenStatusResponse(
    val seller: String? = null,
    val saturday: String?,
    val sunday: String?,
    val monday: String?,
    val tuesday: String?,
    val wednesday: String?,
    val thursday: String?,
    val friday: String?
)