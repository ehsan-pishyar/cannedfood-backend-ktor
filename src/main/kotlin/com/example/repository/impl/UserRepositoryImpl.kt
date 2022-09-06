package com.example.repository.impl

import com.example.authentication.hash
import com.example.models.User
import com.example.repository.UserRepository
import com.example.tables.DatabaseFactory.dbQuery
import com.example.tables.UserTable
import com.example.utils.ErrorCode
import com.example.utils.ServiceResult
import com.example.utils.randomIdGenerator
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * پیاده سازی dao کاربر
 * با استفاده از متد کوئری که ساختیم، میاد عملیات CRUD رو توی پس زمینه برامون انجام میده.
 */
class UserRepositoryImpl : UserRepository {

    /**
     * ثبت کاربر در دیتابیس. با استفاده از اطلاعات کاربری که بهش پاس میدیم میاد این اطلاعات رو توی سلول های جدول قرار میده
     * و در آخر هم کاربری که با موفقیت ثبت کردیم توی دیتابیس رو برامون برمیگردونه تا نشون بدیم
     */
    override suspend fun insertUser(user: User): ServiceResult<User> {
        return try {
            dbQuery {
                UserTable.insert { ut ->
                    ut[id] = randomIdGenerator()
                    ut[email] = user.email
                    ut[password] = hash(user.password)
                    ut[userType] = user.user_type
                    ut[dateCreated] = user.date_created
                }
                    .resultedValues?.single().let { ServiceResult.Success(rowToUser(it)!!) }
            }
        } catch (e: Exception) {
            when(e){
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.USER_EXISTS)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    /**
     * دریافت همه کاربران از دیتابیس. و مرتب سازی کاربران برگردونده شده بر اساس id اونها.
     */
    override suspend fun getUsers(): ServiceResult<List<User?>> {
        return try {
            dbQuery {
                UserTable.selectAll()
                    .orderBy(UserTable.id to SortOrder.ASC)
                    .map(::rowToUser)
            }.let { ServiceResult.Success(it) }
        }catch (e: Exception) {
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getUserById(userId: Long): ServiceResult<User> {
        return try {
            dbQuery {
                UserTable.select {
                    (UserTable.id eq userId)
                }
                    .map { rowToUser(it)!! }
                    .single()
            }.let { ServiceResult.Success(it) }
        } catch (e: Exception) {
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getUsersByEmail(email: String?): ServiceResult<List<User?>> {
        return try {
            dbQuery {
                UserTable.select {
                    (UserTable.email like "$email%")
                }
                    .orderBy(UserTable.id to SortOrder.ASC)
                    .map(::rowToUser)
            }.let { ServiceResult.Success(it) }
        }catch (e: Exception) {
            when (e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }

    }

    /**
     * آپدیت اطلاعات کاربر با استفاده از کاربری که بهش پاس میدیم
     */
    override suspend fun updateUserById(userId: Long, user: User): ServiceResult<User> {
        return try {
            dbQuery {
                UserTable.update({
                    UserTable.id eq userId
                }) {
                    it[id] = userId
                    it[email] = user.email
                    it[password] = hash(user.password)
                    it[userType] = user.user_type
                }
                ServiceResult.Success(selectUserById(userId))
            }
        } catch (e: Exception) {
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    /**
     * عملیات حذف کاربر بر اساس id اونها
     */
    override suspend fun deleteUserById(userId: Long): ServiceResult<List<User?>> {
        return try {
            dbQuery {
                UserTable.deleteWhere {
                    (UserTable.id eq userId)
                }
                ServiceResult.Success(selectUsers())
            }
        } catch (e: Exception) {
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    override suspend fun deleteUsers(): ServiceResult<List<User?>> {
        return try {
            dbQuery {
                UserTable.deleteAll()
                ServiceResult.Success(selectUsers())
            }
        } catch (e: Exception) {
            when(e) {
                is ExposedSQLException -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }
    }

    /**
     * چون به صورت پیش فرض موقعی که میخوایم یه کاربری رو به عنوان مثال از دیتابیس برگردونیم، خوب این کاربر به صورت یه آبجکت user برامون بر نمیگردونه
     * در عوض یه سطر یا یه row برامون برمیگردونه. برای اینکه این سطر رو تبدیل به آبجکت user کنیم تا بتونیم به کاربر نمایشش بدیم، باید با استفاده از این تابع،
     * اون سطر رو تبدیل به آبجکت user کنیم.
     */
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

    private fun selectUsers(): List<User?> {
        return transaction {
            UserTable.selectAll()
                .orderBy(UserTable.dateCreated to SortOrder.DESC)
                .map { rowToUser(it) }
        }
    }

    private fun selectUserById(id: Long): User {
        return transaction {
            UserTable.select {
                (UserTable.id eq id)
            }
                .map { rowToUser(it)!! }
                .single()
        }
    }
}