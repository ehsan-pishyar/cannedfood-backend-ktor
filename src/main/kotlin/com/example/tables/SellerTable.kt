package com.example.tables

import org.jetbrains.exposed.sql.Table

object SellerTable: Table() {

    val id = integer("id").uniqueIndex().autoIncrement()
    val title = varchar(name = "title", length = 50)
    val description = varchar(name = "description", length = 500)
    val logo = varchar(name = "logo", length = 500)
    val banner = varchar(name = "banner", length = 500)
    val sellerCategoryId = integer("seller_category_id") references SellerCategoryTable.id
    val resultCategoryId = integer(name = "result_category_id") references ResultCategoryTable.id
    val foodCategoryId = integer(name = "food_category_id") references FoodCategoryTable.id
    val stateId = integer("stateId") references StateTable.id
    val cityId = integer("cityId") references CityTable.id
    val location = varchar(name = "location", length = 100)
    val resultsId = integer("results_id") references ResultsTable.id
    val isOpen = bool("is_open")
    val rating = double("rating")
    val voteCount = integer("vote_count")
    val deliveryFee = integer("delivery_fee")
    val deliveryDuration = integer("delivery_duration")

    override val primaryKey = PrimaryKey(id, name = "PK_SELLER_ID")
}