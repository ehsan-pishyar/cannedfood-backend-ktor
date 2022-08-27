package com.example.tables

import org.jetbrains.exposed.sql.Table

object SellerCategoryTable: Table() {
    val id = integer("id").autoIncrement().uniqueIndex()
    val title = varchar(name = "title", length = 50)
    val imagePath = varchar(name = "image_path", length = 500).nullable().default("")

    override val primaryKey = PrimaryKey(id, name = "PK_SELLER_CATEGORY_ID")
}

object ResultCategoryTable: Table() {
    val id = integer("id").uniqueIndex().autoIncrement()
    val title = varchar(name = "title", length = 50)
    val imagePath = varchar(name = "image_path", length = 500).nullable().default("")
    val sellerCategoryId = integer("seller_category_id") references SellerCategoryTable.id

    override val primaryKey = PrimaryKey(id, name = "PK_RESULT_CATEGORY_ID")
}

object FoodCategoryTable: Table() {
    val id = integer("id").uniqueIndex().autoIncrement()
    val title = varchar(name = "title", length = 50)
    val imagePath = varchar(name = "image_path", length = 500).nullable().default("")
    val resultCategoryId = integer("result_category_id") references ResultCategoryTable.id

    override val primaryKey = PrimaryKey(id, name = "PK_FOOD_CATEGORY_ID")
}