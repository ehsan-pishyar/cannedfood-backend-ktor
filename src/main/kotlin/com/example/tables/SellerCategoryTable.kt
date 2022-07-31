package com.example.tables

import org.jetbrains.exposed.sql.Table

object SellerCategoryTable: Table() {

    val id = integer("id").autoIncrement().uniqueIndex()
    val title = varchar(name = "title", length = 50)

    override val primaryKey = PrimaryKey(id, name = "PK_SELLER_CATEGORY_ID")
}