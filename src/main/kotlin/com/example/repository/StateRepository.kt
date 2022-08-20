package com.example.repository

import com.example.models.responses.StateResponse
import com.example.utils.ServiceResult

interface StateRepository {

    suspend fun getStates(): ServiceResult<List<StateResponse?>>

    suspend fun getStateById(stateId: Int): ServiceResult<StateResponse?>
    suspend fun getStatesByTitle(stateTitle: String): ServiceResult<List<StateResponse?>>
}