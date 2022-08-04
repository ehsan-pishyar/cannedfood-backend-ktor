package com.example.datasource

import com.example.models.User
import com.example.repository.UserRepository
import com.example.tables.DatabaseFactory.dbQuery
import com.example.tables.UserTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.UUID

class UserRepositoryImpl : UserRepository {

    override suspend fun insertUser(user: User) {
        dbQuery {
            UserTable.insert {ut ->
                ut[username] = user.username
                ut[password] = user.password
            }
        }
    }

    override suspend fun getUsers(): List<User?> {
        val users = dbQuery {
            UserTable.selectAll().map {
                rowToUser(it)
            }
        }
        return users
    }

    override suspend fun getUserById(userId: Int): User {
        val user = dbQuery {
            UserTable.select { UserTable.id.eq(userId) }
                .map {
                    rowToUser(it)
                }
                .singleOrNull()
        }
        return user!!
    }

    override suspend fun getUserByUsername(userName: String?): List<User?> {
        val users = dbQuery {
            UserTable.select { UserTable.username.like(userName!!) }
                .map {
                    rowToUser(it)
                }
        }
        return users
    }

    override suspend fun updateUser(userId: Int, user: User) {
        dbQuery {
            UserTable.update({ UserTable.id.eq(userId) }) {
                it[id] = user.id
                it[username] = user.username
                it[password] = user.password
            }
        }
    }

    override suspend fun deleteUser(userId: Int) {
        dbQuery {
            UserTable.deleteWhere {
                UserTable.id.eq(userId)
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
            username = row[UserTable.username],
            password = row[UserTable.password]
        )
    }
}