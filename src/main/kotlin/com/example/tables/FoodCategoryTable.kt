package com.example.tables

import org.jetbrains.exposed.sql.Table

object FoodCategoryTable: Table() {

    val id = integer("id").uniqueIndex().autoIncrement()
    val title = varchar(name = "title", length = 50)
    val foodCategoryId = integer("food_category_id") references ResultCategoryTable.id

    override val primaryKey = PrimaryKey(id, name = "PK_FOOD_CATEGORY_ID")
}