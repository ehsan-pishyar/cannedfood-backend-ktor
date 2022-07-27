package com.example.tables

import org.jetbrains.exposed.sql.Table

object StateTable: Table() {

    val id = integer("id").uniqueIndex()
    val title = varchar(name = "title", length = 50)

    override val primaryKey = PrimaryKey(id, name = "PK_STATE_ID")
}