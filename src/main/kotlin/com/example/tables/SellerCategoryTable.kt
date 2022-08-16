package com.example.tables

import org.jetbrains.exposed.sql.Table

object SellerCategoryTable: Table() {

    val id = integer("id").autoIncrement().uniqueIndex()
    val title = varchar(name = "title", length = 50)
    val imagePath = varchar(name = "image_path", length = 500)

    override val primaryKey = PrimaryKey(id, name = "PK_SELLER_CATEGORY_ID")
}