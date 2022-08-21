package com.example.usecases

import com.example.models.Results
import com.example.repository.ResultsRepository
import com.example.utils.ServiceResult
import com.example.utils.toDatabaseString
import java.time.LocalDateTime

class InsertResultUseCase(
    private val resultsRepository: ResultsRepository
) {

    suspend operator fun invoke(result: ServiceResult<Results>): Results? {
        return when(result) {
            is ServiceResult.Success -> {
                val response = resultsRepository.insertResult(result.data.copy(
                    date_created = LocalDateTime.now().toDatabaseString()
                ))
                when(response) {
                    is ServiceResult.Success -> response.data
                    else -> null
                }
            }
            is ServiceResult.Error -> null
        }
    }
}