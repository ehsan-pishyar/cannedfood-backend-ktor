package com.example.repository

import com.example.models.State

interface StateRepository {

    suspend fun getStates(): List<State?>
    suspend fun getStateByTitle(stateTitle: String): List<State?>
}