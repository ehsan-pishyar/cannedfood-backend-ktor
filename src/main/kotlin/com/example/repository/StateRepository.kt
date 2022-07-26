package com.example.repository

import com.example.models.State

interface StateRepository {

    suspend fun insertState(stateTitle: String?)

    suspend fun getStates(): List<State?>
    suspend fun getStateById(stateId: Int): State?
    suspend fun getStateByTitle(stateTitle: String): State?

    suspend fun updateStateById(stateId: Int): State
    suspend fun updateStateByTitle(stateTitle: String): State

    suspend fun deleteStateById(stateId: Int)
    suspend fun deleteStateByTitle(stateTitle: String)
    suspend fun deleteStates()
}