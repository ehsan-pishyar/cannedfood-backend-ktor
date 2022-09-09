package com.example.repository.impl

import com.example.models.Seller
import com.example.models.SellerCloseHour
import com.example.models.SellerOpenHour
import com.example.models.responses.SellerOpenStatusResponse
import com.example.repository.SellerOpenStatusRepository
import com.example.tables.DatabaseFactory.dbQuery
import com.example.tables.SellerCloseHourTable
import com.example.tables.SellerOpenHourTable
import com.example.tables.SellerTable
import com.example.utils.ErrorCode
import com.example.utils.ServiceResult
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class SellerOpenStatusRepositoryImpl : SellerOpenStatusRepository {

    override suspend fun insertSellerOpenHours(openHours: SellerOpenHour): ServiceResult<SellerOpenHour> {
        return try {
            dbQuery {
                SellerOpenHourTable.insert {
                    it[sellerId] = openHours.seller_id
                    it[saturday] = openHours.saturday
                    it[sunday] = openHours.sunday
                    it[monday] = openHours.monday
                    it[tuesday] = openHours.tuesday
                    it[wednesday] = openHours.wednesday
                    it[thursday] = openHours.thursday
                    it[friday] = openHours.friday
                }
                    .resultedValues?.single().let {
                        ServiceResult.Success(rowToSellerOpenHours(it)!!)
                    }
            }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun insertSellerCloseHours(closeHours: SellerCloseHour): ServiceResult<SellerCloseHour> {
        return try {
            dbQuery {
                SellerCloseHourTable.insert {
                    it[sellerId] = closeHours.seller_id
                    it[saturday] = closeHours.saturday
                    it[sunday] = closeHours.sunday
                    it[monday] = closeHours.monday
                    it[tuesday] = closeHours.tuesday
                    it[wednesday] = closeHours.wednesday
                    it[thursday] = closeHours.thursday
                    it[friday] = closeHours.friday
                }
                    .resultedValues?.single().let {
                        ServiceResult.Success(rowToSellerCloseHours(it)!!)
                    }
            }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getSellerOpenHoursStatusById(sellerId: Long): ServiceResult<SellerOpenStatusResponse?> {
        lateinit var sellerOpenStatusResponse: SellerOpenStatusResponse
        return try {
            dbQuery {
                val seller = transaction {
                    SellerTable.select {
                        (SellerTable.id eq sellerId)
                    }
                        .map { rowToSeller(it) }
                        .singleOrNull()
                }
                val sellerOpenHour = transaction {
                    (SellerOpenHourTable innerJoin SellerTable).select {
                        (SellerOpenHourTable.sellerId eq seller!!.id)
                    }
                        .map { rowToSellerOpenHours(it) }
                        .singleOrNull()
                }
                val sellerCloseHour = transaction {
                    (SellerCloseHourTable innerJoin SellerTable).select {
                        (SellerCloseHourTable.sellerId eq seller!!.id)
                    }
                        .map { rowToSellerCloseHours(it) }
                        .singleOrNull()
                }
                sellerOpenStatusResponse = SellerOpenStatusResponse(
                    seller = seller!!.title,
                    saturday = "${sellerOpenHour!!.saturday} - ${sellerCloseHour!!.saturday}",
                    sunday = "${sellerOpenHour.sunday} - ${sellerCloseHour.sunday}",
                    monday = "${sellerOpenHour.monday} - ${sellerCloseHour.monday}",
                    tuesday = "${sellerOpenHour.tuesday} - ${sellerCloseHour.tuesday}",
                    wednesday = "${sellerOpenHour.wednesday} - ${sellerCloseHour.wednesday}",
                    thursday = "${sellerOpenHour.thursday} - ${sellerCloseHour.thursday}",
                    friday = "${sellerOpenHour.friday} - ${sellerCloseHour.friday}",
                )
            }.let {
                ServiceResult.Success(sellerOpenStatusResponse)
            }
        } catch (e: Exception) {
            println(e)
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun updateSellerOpenHours(sellerId: Long, openHours: SellerOpenHour): ServiceResult<SellerOpenHour> {
        return try {
            dbQuery {
                SellerOpenHourTable.update({
                    (SellerOpenHourTable.sellerId eq sellerId)
                }) {
                    it[saturday] = openHours.saturday
                    it[sunday] = openHours.sunday
                    it[monday] = openHours.monday
                    it[tuesday] = openHours.saturday
                    it[wednesday] = openHours.wednesday
                    it[thursday] = openHours.thursday
                    it[friday] = openHours.friday
                }
                SellerOpenHourTable.select {
                    (SellerOpenHourTable.sellerId eq sellerId)
                }
                    .map { rowToSellerOpenHours(it) }
                    .single()
            }.let {
                ServiceResult.Success(it!!)
            }
        } catch (e: Exception) {
            println(e)
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun updateSellerCloseHours(sellerId: Long, closeHours: SellerCloseHour): ServiceResult<SellerCloseHour> {
        return try {
            dbQuery {
                SellerCloseHourTable.update({
                    (SellerCloseHourTable.sellerId eq sellerId)
                }) {
                    it[saturday] = closeHours.saturday
                    it[sunday] = closeHours.sunday
                    it[monday] = closeHours.monday
                    it[tuesday] = closeHours.saturday
                    it[wednesday] = closeHours.wednesday
                    it[thursday] = closeHours.thursday
                    it[friday] = closeHours.friday
                }
                SellerCloseHourTable.select {
                    (SellerCloseHourTable.sellerId eq sellerId)
                }
                    .map { rowToSellerCloseHours(it) }
                    .single()
            }.let {
                ServiceResult.Success(it!!)
            }
        } catch (e: Exception) {
            println(e)
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun deleteSellerOpenHoursById(sellerId: Long): ServiceResult<List<SellerOpenHour?>> {
        return try {
            dbQuery {
                SellerOpenHourTable.deleteWhere {
                    (SellerOpenHourTable.sellerId eq sellerId)
                }
                ServiceResult.Success(selectSellerOpenHours())
            }
        } catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun deleteSellersOpenHours(): ServiceResult<List<SellerOpenHour?>> {
        return try {
            dbQuery {
                SellerOpenHourTable.deleteAll()
                ServiceResult.Success(selectSellerOpenHours())
            }
        } catch (e: Exception) {
            println(e)
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun deleteSellerCloseHoursById(sellerId: Long): ServiceResult<List<SellerCloseHour?>> {
        return try {
            dbQuery {
                SellerCloseHourTable.deleteWhere {
                    (SellerCloseHourTable.sellerId eq sellerId)
                }
                ServiceResult.Success(selectSellerCloseHours())
            }
        } catch (e: Exception) {
            println(e)
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun deleteSellersCloseHours(): ServiceResult<List<SellerCloseHour?>> {
        return try {
            dbQuery {
                SellerCloseHourTable.deleteAll()
                ServiceResult.Success(selectSellerCloseHours())
            }
        } catch (e: Exception) {
            println(e)
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    private fun rowToSellerOpenHours(row: ResultRow?): SellerOpenHour? {
        if (row == null) return null

        return SellerOpenHour(
            id = row[SellerOpenHourTable.id],
            seller_id = row[SellerOpenHourTable.sellerId],
            saturday = row[SellerOpenHourTable.saturday],
            sunday = row[SellerOpenHourTable.sunday],
            monday = row[SellerOpenHourTable.monday],
            tuesday = row[SellerOpenHourTable.tuesday],
            wednesday = row[SellerOpenHourTable.wednesday],
            thursday = row[SellerOpenHourTable.thursday],
            friday = row[SellerOpenHourTable.friday]
        )
    }

    private fun rowToSellerCloseHours(row: ResultRow?): SellerCloseHour? {
        if (row == null) return null

        return SellerCloseHour(
            id = row[SellerCloseHourTable.id],
            seller_id = row[SellerCloseHourTable.sellerId],
            saturday = row[SellerCloseHourTable.saturday],
            sunday = row[SellerCloseHourTable.sunday],
            monday = row[SellerCloseHourTable.monday],
            tuesday = row[SellerCloseHourTable.tuesday],
            wednesday = row[SellerCloseHourTable.wednesday],
            thursday = row[SellerCloseHourTable.thursday],
            friday = row[SellerCloseHourTable.friday]
        )
    }

    private fun rowToSeller(row: ResultRow?): Seller? {
        if (row == null) return null

        return Seller(
            id = row[SellerTable.id],
            title = row[SellerTable.title]
        )
    }

    private fun selectSellerOpenHours(): List<SellerOpenHour?> {
        return transaction {
            SellerOpenHourTable.selectAll()
                .orderBy(SellerOpenHourTable.id to SortOrder.ASC)
                .map { rowToSellerOpenHours(it) }
        }
    }

    private fun selectSellerCloseHours(): List<SellerCloseHour?> {
        return transaction {
            SellerCloseHourTable.selectAll()
                .orderBy(SellerCloseHourTable.id to SortOrder.ASC)
                .map { rowToSellerCloseHours(it) }
        }
    }
}