package com.example.datasource

import com.example.models.City
import com.example.models.Location
import com.example.models.State
import com.example.models.responses.LocationResponse
import com.example.repository.LocationRepository
import com.example.tables.CityTable
import com.example.tables.DatabaseFactory.dbQuery
import com.example.tables.LocationTable
import com.example.tables.StateTable
import com.example.utils.ErrorCode
import com.example.utils.ServiceResult
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*

class LocationRepositoryImpl: LocationRepository {

    override suspend fun insertLocation(location: Location): ServiceResult<Location> {
        TODO("Not yet implemented")
    }

    override suspend fun getLocations(cityId: Int): ServiceResult<List<LocationResponse?>> {
        return try {
            val locations = dbQuery {
                (LocationTable innerJoin CityTable innerJoin StateTable)
                    .select {
                        LocationTable.cityId eq cityId
                    }
                    .orderBy(LocationTable.id to SortOrder.ASC)
                    .map { rowToLocationResponse(it) }
            }
            ServiceResult.Success(locations)
        } catch (e: Exception) {
            println(e)
            when (e) {
                is ExposedSQLException -> {
                    println("An Error occurred due to response user by username")
                    ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                }
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getLocation(locationId: Int): ServiceResult<LocationResponse?> {
        TODO("Not yet implemented")
    }

    override suspend fun getLocationsByTitle(title: String?): ServiceResult<List<LocationResponse?>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateLocation(location: Location) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteLocation(locationId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteLocations() {
        TODO("Not yet implemented")
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

    private fun rowToLocationResponse(row: ResultRow?): LocationResponse? {
        if (row == null) return null

        return LocationResponse(
            location_title = row[LocationTable.title],
            lat = row[LocationTable.lat],
            lon = row[LocationTable.lon]
        )
    }
}