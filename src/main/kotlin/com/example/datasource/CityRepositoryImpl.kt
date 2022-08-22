package com.example.datasource

import com.example.models.City
import com.example.repository.CityRepository
import com.example.tables.CityTable
import com.example.tables.DatabaseFactory.dbQuery
import com.example.tables.StateTable
import com.example.utils.ErrorCode
import com.example.utils.ServiceResult
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*

class CityRepositoryImpl : CityRepository {

    override suspend fun insertCity(city: City): ServiceResult<City?> {
        return try {
            dbQuery {
                CityTable.insert {
                    it[title] = city.title
                    it[stateId] = city.state_id
                }
                    .resultedValues?.singleOrNull()?.let {
//                        val stateResponse = getState(city.state_id)
                        ServiceResult.Success(rowToCity(it))
                    } ?: ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getCities(stateId: Int): ServiceResult<List<City?>> {
        return try {
            dbQuery {
                (CityTable innerJoin StateTable).select {
                    CityTable.stateId eq stateId
                }
                    .orderBy(CityTable.id to SortOrder.ASC)
                    .map { rowToCity(it)!! }
            }.let {
                ServiceResult.Success(it)
            }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }

//        return try {
//            val responses: ArrayList<CityResponse> = ArrayList()
//
//            val cities = dbQuery {
//                CityTable.select {
//                    (CityTable.stateId eq stateId)
//                }.map {
//                    rowToCity(it)
//                }
//            }
//            val state = getState(stateId)
//
//            for (city in cities) {
//                responses.add(CityResponse(city?.id!!, city.title, state))
//            }
//            return ServiceResult.Success(responses.toList())
//        } catch (e: Exception) {
//            when (e) {
//                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
//                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
//            }
//        }


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

    override suspend fun getCityById(stateId: Int, cityId: Int): ServiceResult<City?> {
        return try {
            dbQuery {
                CityTable.select {
                    (CityTable.stateId eq stateId) and (CityTable.id eq cityId)
                }.map { rowToCity(it) }
                    .singleOrNull()
            }.let {
                ServiceResult.Success(it)
            }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }

//        val city = dbQuery {
//            CityTable.select {
//                (CityTable.stateId eq stateId) and (CityTable.id eq cityId)
//            }.map {
//                rowToCity(it)
//            }.singleOrNull()
//        }
//        val state = dbQuery {
//            StateTable.select {
//                (StateTable.id eq city?.state_id!!)
//            }.map {
//                rowToState(it)
//            }.singleOrNull()
//        }
//        return if (city != null && state != null) {
//            ServiceResult.Success(CityResponse(city.id, city.title, state))
//        } else {
//            ServiceResult.Error(ErrorCode.DATABASE_ERROR)
//        }

    }

    override suspend fun getCitiesByTitle(stateId: Int, cityTitle: String?): ServiceResult<List<City?>> {
        return try {
            dbQuery {
                CityTable.select {
                    (CityTable.stateId eq stateId) and (CityTable.title like "$cityTitle%")
                }
                    .orderBy(CityTable.id to SortOrder.ASC)
                    .map { rowToCity(it) }
            }.let {
                ServiceResult.Success(it)
            }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }


    override suspend fun updateCity(cityId: Int, city: City): ServiceResult<City> {
        return try {
            val id = dbQuery {
                CityTable.update({
                    CityTable.id eq cityId
                }) {
                    it[id] = city.id
                    it[title] = city.title
                    it[stateId] = city.state_id
                }
            }
            dbQuery {
                CityTable.select {
                    CityTable.id eq id
                }.map { rowToCity(it) }
                    .singleOrNull()
            }.let {
                ServiceResult.Success(it!!)
            }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun deleteCity(cityId: Int) {
        dbQuery {
            CityTable.deleteWhere {
                CityTable.id eq cityId
            }
        }
    }

    override suspend fun deleteCitiesOfState(stateId: Int) {
        dbQuery {
            CityTable.deleteWhere {
                CityTable.stateId eq stateId
            }
        }
    }

    override suspend fun deleteCities() {
        dbQuery {
            CityTable.deleteAll()
        }
    }

    private fun rowToCity(row: ResultRow?): City? {
        if (row == null) return null

        return City(
            id = row[CityTable.id],
            title = row[CityTable.title],
            state_id = row[CityTable.stateId]
        )
    }

//    private fun getState(stateId: Int): State? {
//        return transaction {
//            StateTable.select {
//                StateTable.id eq stateId
//            }.map {
//                rowToState(it)
//            }.singleOrNull()
//        }
//    }

}