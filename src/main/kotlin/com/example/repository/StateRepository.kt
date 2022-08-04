package com.example.repository

import com.example.models.State

interface StateRepository {

    suspend fun getStates(): List<State?>
    suspend fun getStatesByTitle(stateTitle: String): List<State?>
}