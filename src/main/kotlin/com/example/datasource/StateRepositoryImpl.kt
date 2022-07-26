package com.example.datasource

import com.example.models.State
import com.example.repository.StateRepository

class StateRepositoryImpl : StateRepository {

    override suspend fun getStates(): List<State> {
        TODO("Not yet implemented")
    }

    override suspend fun getStateById(stateId: Int): State {
        TODO("Not yet implemented")
    }

    override suspend fun getStateByTitle(stateTitle: String): State? {
        TODO("Not yet implemented")
    }
}