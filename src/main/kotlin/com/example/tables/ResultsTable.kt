package com.example.tables

import com.example.utils.toDatabaseString
import org.jetbrains.exposed.sql.Table
import java.time.LocalDateTime

object ResultsTable: Table() {
    val id = long("id").uniqueIndex()
    val sellerId = long("seller_id") references SellerTable.id
    val title = varchar(name = "title", length = 100)
    val description = varchar(name = "description", length = 500).nullable()
    val sellerCategoryId = integer("seller_category_id") references SellerCategoryTable.id
    val resultCategoryId = integer("result_category_id") references ResultCategoryTable.id
    val foodCategoryId = integer("food_category_id") references FoodCategoryTable.id
    val imagePath = varchar(name = "image_path", length = 500)
    val price = long("price")
    val discount = integer("discount").nullable()
    val prepareDuration = integer("prepare_duration").nullable()
    val dateCreated = varchar(name = "date_created", length = 20).default(LocalDateTime.now().toDatabaseString())

    override val primaryKey = PrimaryKey(id, name = "PK_RESULT_ID")
}