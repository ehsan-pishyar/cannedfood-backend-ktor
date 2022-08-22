package com.example.repository

import com.example.models.User
import com.example.utils.ServiceResult

/**
 * توابع dao برای اضافه کردن کاربر به دیتابیس، دریافت کاربر از دیتابیس، آپدیت اون یا حذفشون
 */
interface UserRepository {

    suspend fun insertUser(user: User): ServiceResult<User?>

    suspend fun getUsers(): ServiceResult<List<User?>>
    suspend fun getUserById(userId: Int): ServiceResult<User?>
    suspend fun getUsersByEmail(email: String): ServiceResult<List<User?>>

    suspend fun updateUser(userId: Int, user: User): ServiceResult<User>

    suspend fun deleteUser(userId: Int)
    suspend fun deleteUsers()
}