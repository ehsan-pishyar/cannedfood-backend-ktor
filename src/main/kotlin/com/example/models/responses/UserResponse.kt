package com.example.models.responses

import com.example.models.ResponseErrors
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val user: User?,
    val errors: List<ResponseErrors> = emptyList()
)
