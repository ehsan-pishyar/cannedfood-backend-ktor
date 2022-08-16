package com.example.models

import com.example.utils.ErrorCode
import kotlinx.serialization.Serializable

@Serializable
data class ResponseErrors(
    val code: ErrorCode,
    val message: String
)
