package com.example.tables

import org.jetbrains.exposed.sql.Table

object FoodCategoryTable: Table() {

    val id = integer("id").uniqueIndex().autoIncrement()
    val title = varchar(name = "title", length = 50)
    val sellerCategoryId = integer("seller_category_id") references SellerCategoryTable.id
    val resultCategoryId = integer("result_category_id") references ResultCategoryTable.id

    override val primaryKey = PrimaryKey(id, name = "PK_FOOD_CATEGORY_ID")
}