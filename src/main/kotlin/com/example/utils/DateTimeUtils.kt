package com.example.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * تابعی که تاریخ و زمان ثبت نام کاربر یا محصول رو با فرمتی که برش مشخص میکنیم برامون انجام میده
 */

private const val DATE_FORMAT = "yyyy-MM-dd hh:mm:ss"

fun LocalDateTime.toDatabaseString(): String {
    val formatter = DateTimeFormatter.ofPattern(DATE_FORMAT)
    return formatter.format(this)
}