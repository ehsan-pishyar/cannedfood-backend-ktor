package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Seller(
    val id: Int,
    val title: String,
    val description: String?,
    val logo: String?,
    val banner: String?,
    val seller_category_id: Int, // رستوران، کافه
    val result_category_id: Int, // فست فود، ایرانی، بین المللی
    val food_category_id: Int?,
    val state_id: Int,
    val city_id: Int,
    val location: String,
    val results_id: Int,
    val is_open: Boolean?,
    val rating: Double?,
    val vote_count: Int?,
    val delivery_fee: Int,
    val delivery_duration: Int,
    val user_id: Int
)
