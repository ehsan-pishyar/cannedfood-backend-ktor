package com.example.repository.impl

import com.example.models.State
import com.example.repository.StateRepository
import com.example.tables.DatabaseFactory.dbQuery
import com.example.tables.StateTable
import com.example.utils.ErrorCode
import com.example.utils.ServiceResult
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*

class StateRepositoryImpl : StateRepository {

    override suspend fun getStates(): ServiceResult<List<State>> {
        return try {
            dbQuery {
                StateTable.selectAll()
                    .orderBy(StateTable.id to SortOrder.ASC)
                    .map { rowToState(it)!! }
            }.let { ServiceResult.Success(it) }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getStateById(stateId: Int): ServiceResult<State> {
        return try {
            dbQuery {
                StateTable.select {
                    StateTable.id eq stateId
                }.map { rowToState(it)!! }
                    .single()
            }.let { ServiceResult.Success(it) }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getStatesByTitle(stateTitle: String): ServiceResult<List<State?>> {
        return try {
            dbQuery {
                StateTable.select {
                    StateTable.title like "$stateTitle%"
                }.orderBy(StateTable.id to SortOrder.ASC)
                    .map { rowToState(it) }
            }.let { ServiceResult.Success(it) }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    private fun rowToState(row: ResultRow?): State? {
        if (row == null) return null

        return State(
            id = row[StateTable.id],
            title = row[StateTable.title],
        )
    }

//    private fun columnToCity(cityTable: CityTable?): City? {
//        if (cityTable == null) return null
//
//        return City(
//            title = cityTable.title.name
//        )
//    }

//    private fun getCities(stateId: Int): City {
//        val cities = transaction {
//            CityTable.select {
//                CityTable.stateId eq stateId
//            }
//                .orderBy(CityTable.id to SortOrder.ASC)
//                .map { rowToCity(it) }
//                .singleOrNull()
//        }
//
//        return cities!!
//    }
}