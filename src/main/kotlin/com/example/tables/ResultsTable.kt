package com.example.tables

import org.jetbrains.exposed.sql.Table

object ResultsTable: Table() {
    val id = long("id").uniqueIndex()
    val sellerId = long("seller_id") references SellerTable.id
    val title = varchar(name = "title", length = 100)
    val description = varchar(name = "description", length = 500).nullable()
    val foodCategoryId = integer("food_category_id") references FoodCategoryTable.id
    val imagePath = varchar(name = "image_path", length = 500)
    val price = long("price")
    val discount = integer("discount").nullable()
    val dateCreated = varchar(name = "date_created", length = 20)
    val prepareDuration = integer("prepare_duration").nullable()

    override val primaryKey = PrimaryKey(id, name = "PK_RESULT_ID")
}