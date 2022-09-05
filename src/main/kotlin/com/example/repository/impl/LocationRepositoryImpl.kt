package com.example.repository.impl

import com.example.models.Location
import com.example.models.responses.LocationResponse
import com.example.repository.LocationRepository
import com.example.tables.CityTable
import com.example.tables.DatabaseFactory.dbQuery
import com.example.tables.LocationTable
import com.example.tables.StateTable
import com.example.utils.ErrorCode
import com.example.utils.ServiceResult
import com.example.utils.randomIdGenerator
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class LocationRepositoryImpl: LocationRepository {

    override suspend fun insertLocation(location: Location): ServiceResult<Location?> {
        return try {
            dbQuery {
                LocationTable.insert {
                    it[id] = randomIdGenerator()
                    it[title] = location.title
                    it[lat] = location.lat
                    it[lon] = location.lon
                    it[cityId] = location.city_id
                }
                    .resultedValues?.single().let {
                        ServiceResult.Success(rowToLocation(it)!!)
                    }
            }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getLocations(cityId: Int): ServiceResult<List<LocationResponse?>> {
        return try {
            dbQuery {
                (LocationTable innerJoin CityTable innerJoin StateTable)
                    .select {
                        LocationTable.cityId eq cityId
                    }
                    .orderBy(LocationTable.id to SortOrder.ASC)
                    .map { rowToLocationResponse(it)!! }
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

    override suspend fun getLocationById(locationId: Long): ServiceResult<LocationResponse?> {
        return try {
            dbQuery {
                (LocationTable innerJoin CityTable innerJoin StateTable)
                    .select { LocationTable.id eq locationId }
                        .map { rowToLocationResponse(it)!! }
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
    }

    override suspend fun getLocationsByTitle(locationTitle: String?): ServiceResult<List<LocationResponse?>> {
        return try {
            dbQuery {
                (LocationTable innerJoin CityTable innerJoin StateTable)
                    .select { LocationTable.title like "$locationTitle%" }
                    .map { rowToLocationResponse(it)!! }
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

    override suspend fun getLocationByLatLon(lat: Double, lon: Double): ServiceResult<LocationResponse> {
        return try {
            dbQuery {
                LocationTable.select {
                    (LocationTable.lat eq lat) and (LocationTable.lon eq lon)
                }
                    .map { rowToLocationResponse(it) }
                    .single()
            }.let {
                ServiceResult.Success(it!!)
            }
        } catch (e: Exception) {
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun updateLocation(locationId: Long, location: Location): ServiceResult<Location?> {
        return try {
            dbQuery {
                LocationTable.update({
                    LocationTable.id eq locationId
                }) {
                    it[id] = locationId
                    it[title] = location.title
                    it[lat] = location.lat
                    it[lon] = location.lon
                    it[cityId] = location.city_id
                }

                transaction {
                    LocationTable.select {
                        LocationTable.id eq locationId
                    }
                        .map { rowToLocation(it) }
                        .singleOrNull()
                }.let {
                    ServiceResult.Success(it)
                }
            }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun deleteLocationById(locationId: Long): ServiceResult<List<LocationResponse?>> {
        return try {
            dbQuery {
                LocationTable.deleteWhere {
                    LocationTable.id eq locationId
                }

                transaction {
                    (LocationTable innerJoin CityTable innerJoin StateTable).selectAll()
                        .orderBy(LocationTable.id to SortOrder.ASC)
                        .limit(20)
                        .map { rowToLocationResponse(it) }
                }.let {
                    ServiceResult.Success(it)
                }
            }
        } catch (e: Exception) {
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun deleteLocationsOfCity(cityId: Int): ServiceResult<List<LocationResponse?>> {
        return try {
            dbQuery {
                LocationTable.deleteWhere {
                    LocationTable.cityId eq cityId
                }

                transaction {
                    (LocationTable innerJoin CityTable innerJoin StateTable).select {
                        LocationTable.cityId eq cityId
                    }
                        .orderBy(LocationTable.id to SortOrder.ASC)
                        .limit(20)
                        .map { rowToLocationResponse(it)!! }
                }.let {
                    ServiceResult.Success(it)
                }
            }
        } catch (e: Exception) {
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun deleteLocations(): ServiceResult<List<LocationResponse?>> {
        return try {
            dbQuery {
                LocationTable.deleteAll()

                transaction {
                    (LocationTable innerJoin CityTable innerJoin StateTable).selectAll()
                        .orderBy(LocationTable.id to SortOrder.ASC)
                        .map { rowToLocationResponse(it) }
                }.let {
                    ServiceResult.Success(it)
                }
            }
        } catch (e: Exception) {
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    private fun rowToLocation(row: ResultRow?): Location? {
        if (row == null) return null

        return Location(
            id = row[LocationTable.id],
            title = row[LocationTable.title],
            lat = row[LocationTable.lat],
            lon = row[LocationTable.lon],
            city_id = row[LocationTable.cityId]
        )
    }

    private fun rowToLocationResponse(row: ResultRow?): LocationResponse? {
        if (row == null) return null

        return LocationResponse(
            id = row[LocationTable.id],
            title = row[LocationTable.title],
            lat = row[LocationTable.lat],
            lon = row[LocationTable.lon],
            city = row[CityTable.title],
            state = row[StateTable.title]
        )
    }
}