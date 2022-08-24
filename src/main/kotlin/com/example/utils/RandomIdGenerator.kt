package com.example.utils

fun randomIdGenerator(): Long {
    val min = 1000000000L
    val max = 9999999999L
    return (min..max).random()
}