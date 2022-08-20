package com.example.datasource

import com.example.models.City
import com.example.models.State
import com.example.models.responses.StateResponse
import com.example.repository.StateRepository
import com.example.tables.CityTable
import com.example.tables.DatabaseFactory.dbQuery
import com.example.tables.FoodCategoryTable
import com.example.tables.StateTable
import com.example.utils.ServiceResult
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class StateRepositoryImpl : StateRepository {

    override suspend fun getStates(): ServiceResult<List<StateResponse?>> {
        val states = dbQuery {
            (StateTable innerJoin CityTable)
                .select {
                    StateTable.id eq CityTable.stateId
                }
                .orderBy(StateTable.id to SortOrder.ASC)
                .map { rowToState(it) }
        }
        return ServiceResult.Success(states)
    }

    override suspend fun getStateById(stateId: Int): ServiceResult<StateResponse?> {
        val state = dbQuery {
            StateTable.innerJoin(CityTable, {StateTable.id}, {CityTable.stateId})
                .select {
                    (StateTable.id eq stateId) and (CityTable.stateId eq stateId)
                }
                .orderBy(CityTable.id to SortOrder.ASC)
                .map {
                    rowToState(it)
                }
                .singleOrNull()
        }
        return ServiceResult.Success(state)
    }

    override suspend fun getStatesByTitle(stateTitle: String): ServiceResult<List<StateResponse?>> {
        val state = dbQuery {
            StateTable.select(StateTable.title.eq(stateTitle))
                .orderBy(FoodCategoryTable.fcId to SortOrder.ASC)
                .map {
                rowToState(it)
            }
        }
        return ServiceResult.Success(state)
    }

    private fun rowToState(row: ResultRow?): StateResponse? {
        if (row == null) return null

        return StateResponse(
            state_id = row[StateTable.id],
            state_title = row[StateTable.title],
            cities = getCities(row[StateTable.id])
        )
    }

    private fun columnToCity(cityTable: CityTable?): City? {
        if (cityTable == null) return null

        return City(
            title = cityTable.title.name
        )
    }

    private fun rowToCity(row: ResultRow): City {
        return City(
            id = row[CityTable.id],
            title = row[CityTable.title],
            state_id = row[CityTable.stateId]
        )
    }

    private fun getCities(stateId: Int): City {
        val cities = transaction {
            CityTable.select {
                CityTable.stateId eq stateId
            }
                .orderBy(CityTable.id to SortOrder.ASC)
                .map { rowToCity(it) }
                .singleOrNull()
        }

        return cities!!
    }
}