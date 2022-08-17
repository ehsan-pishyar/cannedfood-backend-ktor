package com.example.utils

/**
 * یه دیتا کلاس global که میایم آبجکتامون رو در اون ذخیره میکنیم برای نمایش
 */
sealed class ServiceResult<out T> {
    data class Success<out T>(val data: T): ServiceResult<T>()
    data class Error(val errorCode: ErrorCode): ServiceResult<Nothing>()
}
