package com.example.tables

import org.jetbrains.exposed.sql.Table

object ResultCategoryTable: Table() {

    val id = integer("id").uniqueIndex().autoIncrement()
    val title = varchar(name = "title", length = 50)

    override val primaryKey = PrimaryKey(id, name = "PK_CATEGORY_ID")
}