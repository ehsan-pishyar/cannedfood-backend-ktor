package com.example.repository

import com.example.models.User

interface UserRepository {

    suspend fun insertUser(user: User)

    suspend fun getUsers(): List<User?>
    suspend fun getUserById(userId: Int): User
    suspend fun getUserByUsername(userName: String?): List<User?>

    suspend fun updateUser(userId: Int, user: User)

    suspend fun deleteUser(userId: Int)
    suspend fun deleteUsers()
}