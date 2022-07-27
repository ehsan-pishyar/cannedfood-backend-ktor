package com.example.repository

import com.example.models.User

interface UserRepository {

    suspend fun insertUser(user: User): User?

    suspend fun getUsers(): List<User?>
    suspend fun getUserById(userId: Int): User?
    suspend fun getUserByUsername(userName: String?): User?

    suspend fun updateUserById(userId: Int): User?
    suspend fun updateUserByUsername(username: String?): User?
    suspend fun updateUser(user: User): User?

    suspend fun deleteUserById(userId: Int)
    suspend fun deleteUserByUsername(username: String?)
    suspend fun deleteUsers()
}