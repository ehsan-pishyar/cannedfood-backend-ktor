package com.example.datasource

import com.example.models.City
import com.example.repository.CityRepository
import com.example.tables.CityTable
import com.example.tables.DatabaseFactory.dbQuery
import com.example.tables.StateTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class CityRepositoryImpl : CityRepository {

    override suspend fun insertCity(stateId: Int, city: City): City? {
        TODO("Not yet implemented")
    }

    override suspend fun getCities(stateId: Int): List<City?> {
        val cities = dbQuery {
            CityTable.select(CityTable.stateId.eq(stateId)).map {
                rowToCity(it)
            }
        }
        return cities
    }

    override suspend fun getCitiesByTitle(stateId: Int, cityTitle: String?): List<City?> {
        val cities = dbQuery {
            CityTable.select(CityTable.stateId.eq(stateId) and CityTable.title.eq(cityTitle!!)).map {
                rowToCity(it)
            }
        }
        return cities
    }

    override suspend fun updateCity(stateId: Int, cityId: Int, city: City): City {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCity(stateId: Int, cityId: Int) {
        dbQuery {
            CityTable.deleteWhere{
                CityTable.stateId.eq(stateId) and CityTable.id.eq(cityId)
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
}