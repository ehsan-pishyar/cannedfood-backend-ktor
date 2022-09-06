package com.example.usecases

import com.example.models.Results
import com.example.repository.ResultsRepository
import com.example.utils.ServiceResult
import com.example.utils.toDatabaseString
import java.time.LocalDateTime

class InsertResultUseCase(private val resultsRepository: ResultsRepository) {

    suspend operator fun invoke(result: Results): Results {
        resultsRepository.insertResult(result.copy(
            date_created = LocalDateTime.now().toDatabaseString()
        )).let {
            return when(it) {
                is ServiceResult.Success -> it.data
                else -> result
            }
        }
    }
}