package com.example.usecases

import com.example.models.ResponseErrors
import com.example.models.User
import com.example.models.responses.UserResponse
import com.example.repository.UserRepository
import com.example.utils.ServiceResult
import com.example.utils.toDatabaseString
import java.time.LocalDateTime

class InsertUserUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(user: ServiceResult<User>): UserResponse {
        return when (user) {
            is ServiceResult.Success -> {
                val response = userRepository.insertUser(user.data.copy(
                    date_created = LocalDateTime.now().toDatabaseString()
                ))
                when(response) {
                    is ServiceResult.Success -> UserResponse(response.data!!, emptyList())
                    is ServiceResult.Error -> UserResponse(user.data, listOf(ResponseErrors(response.errorCode, response.errorCode.message)))
                }
            }
            is ServiceResult.Error -> UserResponse(null, listOf(ResponseErrors(user.errorCode, user.errorCode.message)))
        }
    }
}