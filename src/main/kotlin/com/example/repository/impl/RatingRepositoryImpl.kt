package com.example.repository.impl

import com.example.models.ResultRating
import com.example.models.SellerRating
import com.example.repository.RatingRepository
import com.example.tables.DatabaseFactory.dbQuery
import com.example.tables.ResultRatingTable
import com.example.tables.SellerRatingTable
import com.example.utils.ErrorCode
import com.example.utils.ServiceResult
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*

class RatingRepositoryImpl : RatingRepository {

    override suspend fun addRateToSeller(sellerId: Long, sellerRating: SellerRating): ServiceResult<SellerRating?> {
        return try {
            dbQuery {
                SellerRatingTable.insert {
                    it[fromCustomerId] = sellerRating.from_customer_id
                    it[rating] = sellerRating.rating
                    it[toSellerId] = sellerId
                }
                    .resultedValues?.singleOrNull().let {
                        ServiceResult.Success(rowToSellerRating(it))
                    }
            }
        } catch (e: Exception) {
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun addRateToResult(resultId: Long, resultRating: ResultRating): ServiceResult<ResultRating?> {
        return try {
            dbQuery {
                ResultRatingTable.insert {
                    it[fromCustomerId] = resultRating.from_customer_id
                    it[rating] = resultRating.rating
                    it[toResultId] = resultId
                }
                    .resultedValues?.singleOrNull().let {
                        ServiceResult.Success(rowToResultRating(it))
                    }
            }
        } catch (e: Exception) {
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getSellerRating(sellerId: Long): ServiceResult<Double> {
        lateinit var value: IntArray
        dbQuery {
            SellerRatingTable.slice(SellerRatingTable.rating).select {
                (SellerRatingTable.toSellerId eq sellerId)
            }.forEach {
                value = it.fieldIndex.values.toIntArray()
            }
        }
        return ServiceResult.Success(intArrayToDouble(value))
    }

    override suspend fun getResultRating(resultId: Long): ServiceResult<Double> {
        lateinit var value: IntArray
        dbQuery {
            ResultRatingTable.slice(ResultRatingTable.rating).select {
                (ResultRatingTable.toResultId eq resultId)
            }.forEach {
                value = it.fieldIndex.values.toIntArray()
            }
        }
        return ServiceResult.Success(intArrayToDouble(value))
    }

    private fun rowToSellerRating(row: ResultRow?): SellerRating? {
        if (row == null) return null

        return SellerRating(
            id = row[SellerRatingTable.id],
            from_customer_id = row[SellerRatingTable.fromCustomerId],
            rating = row[SellerRatingTable.rating]
        )
    }

    private fun rowToResultRating(row: ResultRow?): ResultRating? {
        if (row == null) return null

        return ResultRating(
            id = row[ResultRatingTable.id],
            from_customer_id = row[ResultRatingTable.fromCustomerId],
            rating = row[ResultRatingTable.rating]
        )
    }

    private fun intArrayToDouble(value: IntArray): Double {
        var sum = 0
        var result = 0.0

        value.forEach {
            sum + it
        }

        result = (sum / value.size).toDouble()

        return result
    }
}