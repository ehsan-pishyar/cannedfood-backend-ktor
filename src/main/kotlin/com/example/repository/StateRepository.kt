package com.example.repository

import com.example.models.State
import com.example.models.responses.StateResponse
import com.example.utils.ServiceResult

interface StateRepository {

    suspend fun getStates(): ServiceResult<List<State>>
    suspend fun getStateById(stateId: Int): ServiceResult<State>
    suspend fun getStatesByTitle(stateTitle: String): ServiceResult<List<State?>>
}