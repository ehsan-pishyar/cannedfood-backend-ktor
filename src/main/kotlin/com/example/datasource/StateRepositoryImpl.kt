package com.example.datasource

import com.example.models.State
import com.example.repository.StateRepository

class StateRepositoryImpl : StateRepository {

    override suspend fun insertState(stateTitle: String?) {
        TODO("Not yet implemented")
    }

    override suspend fun getStates(): List<State> {
        TODO("Not yet implemented")
    }

    override suspend fun getStateById(stateId: Int): State {
        TODO("Not yet implemented")
    }

    override suspend fun getStateByTitle(stateTitle: String): State? {
        TODO("Not yet implemented")
    }

    override suspend fun updateStateById(stateId: Int): State {
        TODO("Not yet implemented")
    }

    override suspend fun updateStateByTitle(stateTitle: String): State {
        TODO("Not yet implemented")
    }

    override suspend fun deleteStateById(stateId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteStateByTitle(stateTitle: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteStates() {
        TODO("Not yet implemented")
    }
}