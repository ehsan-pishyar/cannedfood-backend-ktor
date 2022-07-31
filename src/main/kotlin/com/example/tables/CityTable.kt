package com.example.tables

import org.jetbrains.exposed.sql.Table

object CityTable: Table() {

    val id = integer("id").uniqueIndex().autoIncrement()
    val title = varchar(name = "title", length = 50)
    val stateId = integer("state_id") references StateTable.id

    override val primaryKey = PrimaryKey(id, name = "PK_CITY_ID")
}