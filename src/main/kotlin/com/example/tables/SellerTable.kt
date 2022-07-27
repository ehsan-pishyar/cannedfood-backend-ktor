package com.example.tables

import org.jetbrains.exposed.sql.Table

object SellerTable: Table() {

    val id = integer("id").uniqueIndex().autoIncrement()
    val title = varchar(name = "title", length = 50)
    val description = varchar(name = "description", length = 500)
    val categoryId = integer(name = "categoryId") references SellerCategoryTable.id
    val stateId = integer("stateId") references StateTable.id
    val cityId = integer("cityId") references CityTable.id
    val location = varchar(name = "location", length = 100)
    val resultsId = integer("resultsId") references ResultsTable.id
    val isOpen = bool("isOpen")
    val deliveryDuration = integer("deliveryTime")
    val deliveryFee = integer("deliveryPrice")

    override val primaryKey = PrimaryKey(id, name = "PK_RESULTS_ID")
}