package com.example.datasource

import com.example.authentication.hash
import com.example.models.User
import com.example.repository.UserRepository
import com.example.tables.DatabaseFactory.dbQuery
import com.example.tables.UserTable
import com.example.utils.ErrorCode
import com.example.utils.ServiceResult
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

/**
 * پیاده سازی dao کاربر
 * با استفاده از متد کوئری که ساختیم، میاد عملیات CRUD رو توی پس زمینه برامون انجام میده.
 */
class UserRepositoryImpl : UserRepository {

    /**
     * ثبت کاربر در دیتابیس. با استفاده از اطلاعات کاربری که بهش پاس میدیم میاد این اطلاعات رو توی سلول های جدول قرار میده
     * و در آخر هم کاربری که با موفقیت ثبت کردیم توی دیتابیس رو برامون برمیگردونه تا نشون بدیم
     */
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

    /**
     * دریافت همه کاربران از دیتابیس. و مرتب سازی کاربران برگردونده شده بر اساس id اونها.
     */
    override suspend fun getUsers(): ServiceResult<List<User?>> {
        return try {
            val users = dbQuery {
                UserTable.selectAll()
                    .orderBy(UserTable.userId to SortOrder.ASC)
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
                UserTable.select(UserTable.userId eq userId)
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
                    .orderBy(UserTable.userId to SortOrder.ASC)
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

    /**
     * آپدیت اطلاعات کاربر با استفاده از کاربری که بهش پاس میدیم
     */
    override suspend fun updateUser(user: User) {
        dbQuery {
            UserTable.update {
                (userId eq user.user_id)
                it[userId] = user.user_id
                it[email] = user.email
                it[password] = user.password
                it[userType] = user.user_type
                it[dateCreated] = user.date_created
            }
        }
    }

    /**
     * عملیات حذف کاربر بر اساس id اونها
     */
    override suspend fun deleteUser(userId: Int) {
        dbQuery {
            UserTable.deleteWhere {
                UserTable.userId eq userId
            }
        }
    }

    override suspend fun deleteUsers() {
        dbQuery {
            UserTable.deleteAll()
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
            user_id = row[UserTable.userId],
            email = row[UserTable.email],
            password = row[UserTable.password],
            user_type = row[UserTable.userType],
            date_created = row[UserTable.dateCreated]
        )
    }
}