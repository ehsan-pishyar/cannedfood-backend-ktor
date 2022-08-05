package com.example.tables

import org.jetbrains.exposed.sql.Table

object UserTable: Table() {

    val id = integer("id").uniqueIndex().autoIncrement()
    val username = varchar(name = "username", length = 50)
    val password = varchar(name = "password", length = 8)
    val isAdmin = bool("is_admin")

    override val primaryKey = PrimaryKey(id, name = "PK_USER_ID")
}