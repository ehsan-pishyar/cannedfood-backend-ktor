package com.example.tables

import org.jetbrains.exposed.sql.Table

object LocationTable: Table() {

    val id = integer("id").uniqueIndex().autoIncrement()
    val title = varchar(name = "title", length = 50)
    val cityId = integer("city_id") references CityTable.id

    override val primaryKey = PrimaryKey(id, name = "PK_LOCATION_ID")
}