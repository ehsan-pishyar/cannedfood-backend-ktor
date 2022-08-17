package com.example.tables

import org.jetbrains.exposed.sql.Table

object SellerCategoryTable: Table() {

    val scId = integer("sc_id").autoIncrement().uniqueIndex()
    val title = varchar(name = "title", length = 50)
    val imagePath = varchar(name = "image_path", length = 500)

    override val primaryKey = PrimaryKey(scId, name = "PK_SELLER_CATEGORY_ID")
}

object ResultCategoryTable: Table() {

    val rcId = integer("rc_id").uniqueIndex().autoIncrement()
    val title = varchar(name = "title", length = 50)
    val imagePath = varchar(name = "image_path", length = 500)
    val sellerCategoryId = integer("seller_category_id") references SellerCategoryTable.scId

    override val primaryKey = PrimaryKey(rcId, name = "PK_RESULT_CATEGORY_ID")
}

object FoodCategoryTable: Table() {

    val fcId = integer("fc_id").uniqueIndex().autoIncrement()
    val title = varchar(name = "title", length = 50)
    val imagePath = varchar(name = "image_path", length = 500)
    val resultCategoryId = integer("result_category_id") references ResultCategoryTable.rcId

    override val primaryKey = PrimaryKey(fcId, name = "PK_FOOD_CATEGORY_ID")
}