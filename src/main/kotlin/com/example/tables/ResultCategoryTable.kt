package com.example.tables

import org.jetbrains.exposed.sql.Table

object ResultCategoryTable: Table() {

    val id = integer("id").uniqueIndex().autoIncrement()
    val title = varchar(name = "title", length = 50)
    val imagePath = varchar(name = "image_path", length = 500)
    val sellerCategoryId = integer("seller_category_id") // references SellerCategoryTable.id

    override val primaryKey = PrimaryKey(id, name = "PK_RESULT_CATEGORY_ID")
}