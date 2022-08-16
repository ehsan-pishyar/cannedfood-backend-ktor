package com.example.tables

import org.jetbrains.exposed.sql.Table

object ResultsTable: Table() {

    val id = integer("id").uniqueIndex().autoIncrement()
    val title = varchar(name = "title", length = 100)
    val description = varchar(name = "description", length = 500).nullable()
    val sellerId = integer("seller_id") references SellerTable.id
    val sellerCategoryId = integer("seller_category_id") references SellerCategoryTable.id
    val resultCategoryId = integer("result_category_id") references ResultCategoryTable.id
    val foodCategoryId = integer("food_category_id") references FoodCategoryTable.id
    val imagePath = varchar(name = "image_path", length = 500)
    val price = integer("price")
    val rating = double("rating").nullable()
    val voteCount = integer("vote_count")
    val discount = integer("discount").nullable()
    val dateAdded = varchar(name = "date_added", length = 20)
    val prepareDuration = integer("prepare_duration")

    override val primaryKey = PrimaryKey(id, name = "PK_RESULT_ID")
}