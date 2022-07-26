package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Seller(
    val id: Int,
    val name: String,
    val description: String?,
    val state_id: Int,
    val city_id: Int,
    val location: String,
    val results_id: List<Int>,
    val is_open: Boolean,
    val delivery_duration: Int,
    val delivery_fee: Int
)
