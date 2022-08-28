package com.example.repository

import com.example.models.User
import com.example.utils.ServiceResult
import java.security.Provider.Service

/**
 * توابع dao برای اضافه کردن کاربر به دیتابیس، دریافت کاربر از دیتابیس، آپدیت اون یا حذفشون
 */
interface UserRepository {

    suspend fun insertUser(user: User): ServiceResult<User?>

    suspend fun getUsers(): ServiceResult<List<User?>>
    suspend fun getUserById(userId: Long): ServiceResult<User>
    suspend fun getUsersByEmail(email: String?): ServiceResult<List<User?>>

    suspend fun updateUserById(userId: Long, user: User): ServiceResult<User>

    suspend fun deleteUserById(userId: Long): ServiceResult<List<User?>>
    suspend fun deleteUsers(): ServiceResult<List<User?>>
}