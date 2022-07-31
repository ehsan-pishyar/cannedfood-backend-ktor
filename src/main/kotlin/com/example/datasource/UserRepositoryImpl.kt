package com.example.datasource

import com.example.models.User
import com.example.repository.UserRepository

class UserRepositoryImpl : UserRepository {

    override suspend fun insertUser(user: User): User? {
        TODO("Not yet implemented")
    }

    override suspend fun getUsers(): List<User?> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(userId: Int): User? {
        TODO("Not yet implemented")
    }

    override suspend fun getUserByUsername(userName: String?): User? {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserById(userId: Int): User? {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserByUsername(username: String?): User? {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(user: User): User? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUserById(userId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUserByUsername(username: String?) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUsers() {
        TODO("Not yet implemented")
    }
}