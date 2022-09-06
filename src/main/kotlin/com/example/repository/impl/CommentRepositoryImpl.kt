package com.example.repository.impl

import com.example.models.ResultComment
import com.example.models.SellerComment
import com.example.models.responses.ResultCommentResponse
import com.example.models.responses.SellerCommentResponse
import com.example.repository.CommentRepository
import com.example.tables.CustomerTable
import com.example.tables.DatabaseFactory.dbQuery
import com.example.tables.ResultCommentTable
import com.example.tables.SellerCommentTable
import com.example.utils.ErrorCode
import com.example.utils.ServiceResult
import com.example.utils.randomIdGenerator
import com.example.utils.toDatabaseString
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import java.time.LocalDateTime

class CommentRepositoryImpl : CommentRepository {
    override suspend fun addCommentForSeller(
        sellerId: Long,
        sellerComment: SellerComment
    ): ServiceResult<SellerComment> {

        return try {
            dbQuery {
                SellerCommentTable.insert {
                    it[id] = randomIdGenerator()
                    it[fromCustomerId] = sellerComment.from_customer_id
                    it[message] = sellerComment.message
                    it[toSellerId] = sellerId
                    it[dateCreated] = LocalDateTime.now().toDatabaseString()
                }
                    .resultedValues?.single().let { ServiceResult.Success(rowToSellerComment(it)!!) }
            }
        } catch (e: Exception) {
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun addCommentForResult(
        resultId: Long,
        resultComment: ResultComment
    ): ServiceResult<ResultComment> {

        return try {
            dbQuery {
                ResultCommentTable.insert {
                    it[id] = randomIdGenerator()
                    it[fromCustomerId] = resultComment.from_customer_id
                    it[message] = resultComment.message
                    it[toResultId] = resultId
                    it[dateCreated] = LocalDateTime.now().toDatabaseString()
                }
                    .resultedValues?.single().let { ServiceResult.Success(rowToResultComment(it)!!) }
            }
        } catch (e: Exception) {
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getSellerComments(sellerId: Long): ServiceResult<List<SellerCommentResponse?>> {
        return try {
            dbQuery {
                (SellerCommentTable innerJoin CustomerTable).select {
                    (SellerCommentTable.toSellerId eq sellerId)
                }
                    .orderBy(SellerCommentTable.dateCreated to SortOrder.DESC)
                    .map { rowToSellerCommentResponse(it) }
            }.let { ServiceResult.Success(it) }
        } catch (e: Exception) {
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getResultComments(resultId: Long): ServiceResult<List<ResultCommentResponse?>> {
        return try {
            dbQuery {
                (ResultCommentTable innerJoin CustomerTable).select {
                    (ResultCommentTable.toResultId eq resultId)
                }
                    .orderBy(ResultCommentTable.dateCreated to SortOrder.DESC)
                    .map { rowToResultCommentResponse(it) }
            }.let { ServiceResult.Success(it) }
        } catch (e: Exception) {
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    private fun rowToSellerComment(row: ResultRow?): SellerComment? {
        if (row == null) return null

        return SellerComment(
            id = row[SellerCommentTable.id],
            from_customer_id = row[SellerCommentTable.fromCustomerId],
            message = row[SellerCommentTable.message],
            date_created = row[SellerCommentTable.dateCreated]
        )
    }

    private fun rowToResultComment(row: ResultRow?): ResultComment? {
        if (row == null) return null

        return ResultComment(
            id = row[ResultCommentTable.id],
            from_customer_id = row[ResultCommentTable.fromCustomerId],
            message = row[ResultCommentTable.message],
            date_created = row[ResultCommentTable.dateCreated]
        )
    }

    private fun rowToSellerCommentResponse(row: ResultRow?): SellerCommentResponse? {
        if (row == null) return null

        return SellerCommentResponse(
            from = row[CustomerTable.firstName],
            message = row[SellerCommentTable.message],
            date_created = row[SellerCommentTable.dateCreated]
        )
    }

    private fun rowToResultCommentResponse(row: ResultRow?): ResultCommentResponse? {
        if (row == null) return null

        return ResultCommentResponse(
            from = row[CustomerTable.firstName],
            message = row[SellerCommentTable.message],
            date_created = row[SellerCommentTable.dateCreated]
        )
    }
}