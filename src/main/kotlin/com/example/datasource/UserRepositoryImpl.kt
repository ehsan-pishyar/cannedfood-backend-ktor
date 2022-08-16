package com.example.datasource

import com.example.authentication.hash
import com.example.models.responses.User
import com.example.models.responses.UserResponse
import com.example.repository.UserRepository
import com.example.tables.DatabaseFactory.dbQuery
import com.example.tables.UserTable
import com.example.utils.ErrorCode
import com.example.utils.ServiceResult
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class UserRepositoryImpl : UserRepository {

    override suspend fun insertUser(user: User): ServiceResult<User?> {
        return try {
            dbQuery {
                UserTable.insert { ut ->
                    ut[email] = user.email
                    ut[password] = hash(user.password)
                    ut[userType] = user.user_type
                    ut[dateCreated] = user.date_created
                }
                    .resultedValues?.singleOrNull()?.let {
                        ServiceResult.Success(rowToUser(it))
                    } ?: ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        } catch (e: Exception) {
            when(e){
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.USER_EXISTS)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getUsers(): ServiceResult<List<User?>> {
        return try {
            val users = dbQuery {
                UserTable.selectAll()
                    .orderBy(UserTable.id to SortOrder.ASC)
                    .map(::rowToUser)
            }
            ServiceResult.Success(users)
        }catch (e: Exception) {
            println(e)
            when(e) {
                is ExposedSQLException -> {
                    println("An Error occurred due to response users")
                    ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                }
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getUserById(userId: Int): ServiceResult<User?> {
        return try {
            val user = dbQuery {
                UserTable.select(UserTable.id eq userId)
                    .map { rowToUser(it) }
                    .singleOrNull()
            }
            ServiceResult.Success(user)
        } catch (e: Exception) {
            println(e)
            when (e) {
                is ExposedSQLException -> {
                    println("An Error occurred due to response user by id")
                    ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                }
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getUserByUsername(userName: String): ServiceResult<List<User?>> {
        return try {
            val users = dbQuery {
                UserTable.select { UserTable.email like "$userName%" }
                    .orderBy(UserTable.id to SortOrder.ASC)
                    .map(::rowToUser)
            }
            ServiceResult.Success(users)
        }catch (e: Exception) {
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

    override suspend fun updateUser(userId: Int, user: User) {
        dbQuery {
            UserTable.update {
                (id eq userId)
                it[id] = user.id
                it[email] = user.email
                it[password] = user.password
                it[userType] = user.user_type
                it[dateCreated] = user.date_created
            }
        }
    }

    override suspend fun deleteUser(userId: Int) {
        dbQuery {
            UserTable.deleteWhere {
                UserTable.id eq userId
            }
        }
    }

    override suspend fun deleteUsers() {
        dbQuery {
            UserTable.deleteAll()
        }
    }

    private fun rowToUser(row: ResultRow?): User? {
        if (row == null) return null

        return User(
            id = row[UserTable.id],
            email = row[UserTable.email],
            password = row[UserTable.password],
            user_type = row[UserTable.userType],
            date_created = row[UserTable.dateCreated]
        )
    }
}