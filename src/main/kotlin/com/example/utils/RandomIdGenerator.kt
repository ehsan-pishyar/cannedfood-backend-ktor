package com.example.utils

import kotlin.math.min

fun randomIdGenerator(): Long {
    val min = 1000000L
    val max = 9999999L
    return (min..max).random()
}