package com.example.tables

import com.example.models.responses.UserType
import org.jetbrains.exposed.sql.Table

object UserTable: Table() {

    val id = integer("id").uniqueIndex().autoIncrement()
    val email = varchar(name = "email", length = 50).uniqueIndex()
    val password = varchar(name = "password", length = 500)
    val userType = enumeration<UserType>(name = "user_type")
    val dateCreated = varchar(name = "date_created", length = 50)

    override val primaryKey = PrimaryKey(id, name = "PK_USER_ID")
}