package com.example.datasource

import com.example.models.State
import com.example.repository.StateRepository
import com.example.tables.DatabaseFactory.dbQuery
import com.example.tables.FoodCategoryTable
import com.example.tables.StateTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class StateRepositoryImpl : StateRepository {

    override suspend fun getStates(): List<State?> {
        val states = dbQuery {
            StateTable.selectAll()
                .orderBy(StateTable.id to SortOrder.ASC)
                .map {
                rowToState(it)
            }
        }
        return states
    }

    override suspend fun getStatesByTitle(stateTitle: String): List<State?> {
        val state = dbQuery {
            StateTable.select(StateTable.title.eq(stateTitle))
                .orderBy(FoodCategoryTable.id to SortOrder.ASC)
                .map {
                rowToState(it)
            }
        }
        return state
    }

    private fun rowToState(row: ResultRow?): State? {
        if (row == null) return null

        return State(
            id = row[StateTable.id],
            title = row[StateTable.title]
        )
    }
}