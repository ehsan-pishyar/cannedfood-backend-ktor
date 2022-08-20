package com.example.datasource

import com.example.models.City
import com.example.models.State
import com.example.models.responses.CityResponse
import com.example.repository.CityRepository
import com.example.tables.CityTable
import com.example.tables.DatabaseFactory.dbQuery
import com.example.tables.StateTable
import com.example.utils.ErrorCode
import com.example.utils.ServiceResult
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class CityRepositoryImpl : CityRepository {

    override suspend fun insertCity(city: City): ServiceResult<CityResponse?> {
        return try {
            dbQuery {
                CityTable.insert {
                    it[title] = city.title
                    it[stateId] = city.state_id
                }
                    .resultedValues?.singleOrNull()?.let {
                        val stateResponse = getState(city.state_id)
                        val cityResponse = rowToCity(it)
                        ServiceResult.Success(CityResponse(cityResponse?.id!!, cityResponse.title, stateResponse))
                    } ?: ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getCities(stateId: Int): ServiceResult<List<CityResponse?>> {
        return try {
            val responses: ArrayList<CityResponse> = ArrayList()

            val cities = dbQuery {
                CityTable.select {
                    (CityTable.stateId eq stateId)
                }.map {
                    rowToCity(it)
                }
            }
            val state = getState(stateId)

            for (city in cities) {
                responses.add(CityResponse(city?.id!!, city.title, state))
            }
            return ServiceResult.Success(responses.toList())
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }


//        return try {
//            val cities = dbQuery {
//                (CityTable innerJoin StateTable).select {
//                    CityTable.stateId eq stateId
//                }
//                    .orderBy(CityTable.id to SortOrder.ASC)
//                    .map { rowToCity(it) }
//            }
//            ServiceResult.Success(cities)
//        } catch (e: Exception) {
//            when (e) {
//                is ExposedSQLException -> ServiceResult.Error(ErrorCode.USER_EXISTS)
//                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
//            }
//        }
    }

    override suspend fun getCityById(stateId: Int, cityId: Int): ServiceResult<CityResponse?> {
        val city = dbQuery {
            CityTable.select {
                (CityTable.stateId eq stateId) and (CityTable.id eq cityId)
            }.map {
                rowToCity(it)
            }.singleOrNull()
        }
        val state = dbQuery {
            StateTable.select {
                (StateTable.id eq city?.state_id!!)
            }.map {
                rowToState(it)
            }.singleOrNull()
        }
        return if (city != null && state != null) {
            ServiceResult.Success(CityResponse(city.id!!, city.title, state))
        } else {
            ServiceResult.Error(ErrorCode.DATABASE_ERROR)
        }

    }

    override suspend fun getCitiesByTitle(stateId: Int, cityTitle: String?): ServiceResult<List<CityResponse?>> {
        val cities = dbQuery {
            CityTable.select {
                (CityTable.stateId eq stateId) and (CityTable.title like "$cityTitle%")
            }.map {
                rowToCity(it)
            }
        }
        val state = dbQuery {
            StateTable.select {
                (StateTable.id eq stateId)
            }.map {
                rowToState(it)
            }.singleOrNull()
        }
        val responses: ArrayList<CityResponse> = ArrayList()

        for (city in cities) {
            responses.add(CityResponse(city?.id!!, city.title, state))
        }
        return ServiceResult.Success(responses.toList())
    }

    override suspend fun updateCity(city: City) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCity(cityId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCitiesOfState(stateId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCities() {
        TODO("Not yet implemented")
    }

    private fun rowToCity(row: ResultRow?): City? {
        if (row == null) return null

        return City(
            id = row[CityTable.id],
            title = row[CityTable.title],
            state_id = row[CityTable.stateId]
        )
    }

    private fun rowToState(row: ResultRow?): State? {
        if (row == null) return null

        return State(
            id = row[StateTable.id],
            title = row[StateTable.title]
        )
    }

    private fun getState(stateId: Int): State? {
        val state = transaction {
            StateTable.select {
                StateTable.id eq stateId
            }.map {
                rowToState(it)
            }.singleOrNull()
        }
        return state
    }

}