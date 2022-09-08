package com.example.utils

fun randomIdGenerator(): Long {
    val min = 100000L
    val max = 999999L
    return (min..max).random()
}

fun initPage(size: Int): Int {
    return if ((size / 20) == 0) {
        1
    } else {
        size
    }
}