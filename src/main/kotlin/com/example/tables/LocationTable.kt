package com.example.tables

import org.jetbrains.exposed.sql.Table

object StateTable: Table() {

    val id = integer("id").uniqueIndex()
    val title = varchar(name = "title", length = 50)

    override val primaryKey = PrimaryKey(id, name = "PK_STATE_ID")
}

object CityTable: Table() {

    val id = integer("id").uniqueIndex().autoIncrement()
    val title = varchar(name = "title", length = 50)
    val stateId = integer("state_id") references StateTable.id

    override val primaryKey = PrimaryKey(id, name = "PK_CITY_ID")
}

object LocationTable: Table() {

    val id = integer("id").uniqueIndex().autoIncrement()
    val title = varchar(name = "title", length = 50)
    val lat = double("lat").default(0.0)
    val lon = double("lon").default(0.0)
    val cityId = integer("city_id") references CityTable.id

    override val primaryKey = PrimaryKey(id, name = "PK_LOCATION_ID")
}