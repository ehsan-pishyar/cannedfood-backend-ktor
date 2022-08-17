package com.example.datasource

import com.example.models.Location
import com.example.repository.LocationRepository
import com.example.tables.DatabaseFactory.dbQuery
import com.example.tables.FoodCategoryTable
import com.example.tables.LocationTable
import org.jetbrains.exposed.sql.*

class LocationRepositoryImpl: LocationRepository {

    override suspend fun insertLocation(location: Location) {
        dbQuery {
            LocationTable.insert {
                it[title] = location.title
                it[cityId] = location.city_id
            }
        }
    }

    override suspend fun getLocations(cityId: Int): List<Location?> {
        val locations = dbQuery {
            LocationTable.select {
                LocationTable.cityId.eq(cityId)
            }
                .orderBy(FoodCategoryTable.fcId to SortOrder.ASC)
                .map {
                rowToLocation(it)
            }
        }
        return locations
    }

    override suspend fun getLocation(locationId: Int): Location {
        val location = dbQuery {
            LocationTable.select {
                LocationTable.id.eq(locationId)
            }.map {
                rowToLocation(it)
            }.singleOrNull()
        }
        return location!!
    }

    override suspend fun getLocationsByTitle(title: String?): List<Location?> {
        val locations = dbQuery {
            LocationTable.select {
                LocationTable.title.like(title!!)
            }
                .orderBy(FoodCategoryTable.fcId to SortOrder.ASC)
                .map {
                rowToLocation(it)
            }
        }
        return locations
    }

    override suspend fun updateLocation(locationId: Int, location: Location) {
        dbQuery {
            LocationTable.update({
                LocationTable.id.eq(locationId)
            }) {
                it[id] = location.id
                it[title] = location.title
                it[cityId] = location.city_id
            }
        }
    }

    override suspend fun deleteLocation(locationId: Int) {
        dbQuery {
            LocationTable.deleteWhere {
                LocationTable.id.eq(locationId)
            }
        }
    }

    override suspend fun deleteLocations() {
        dbQuery {
            LocationTable.deleteAll()
        }
    }

    private fun rowToLocation(row: ResultRow?): Location? {
        if (row == null) return null

        return Location(
            id = row[LocationTable.id],
            title = row[LocationTable.title],
            city_id = row[LocationTable.cityId]
        )
    }
}