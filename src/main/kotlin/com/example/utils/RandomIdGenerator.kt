package com.example.utils

fun randomIdGenerator(): Long {
    val min = 100000L
    val max = 999999L
    return (min..max).random()
}