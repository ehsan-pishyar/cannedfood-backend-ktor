package com.example.repository

import com.example.models.User
import com.example.utils.ServiceResult

interface UserRepository {

    suspend fun insertUser(user: User): ServiceResult<User?>

    suspend fun getUsers(): ServiceResult<List<User?>>
    suspend fun getUserById(userId: Int): ServiceResult<User?>
    suspend fun getUserByUsername(userName: String): ServiceResult<List<User?>>

    suspend fun updateUser(userId: Int, user: User)

    suspend fun deleteUser(userId: Int)
    suspend fun deleteUsers()
}