package com.example.models.responses

import kotlinx.serialization.Serializable

@Serializable
data class SellerCommentResponse(
    val from: String?,
    val message: String?,
    val date_created: String?
)

@Serializable
data class ResultCommentResponse(
    val from: String?,
    val message: String?,
    val date_created: String?
)