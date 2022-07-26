package com.example.tables

import org.jetbrains.exposed.sql.Table

object ResultsTable: Table() {

    val id = integer("id").uniqueIndex().autoIncrement()
    val title = varchar(name = "title", length = 100)
    val description = varchar(name = "description", length = 500)
    val typeId = integer("typeId") references TypeTable.id
    val categoryId = integer("categoryId") references CategoryTable.id
    val imagePath = varchar(name = "imagePath", length = 500)
    val price = integer("price")
    val rating = double("rating")
    val voteCount = integer("voteCount")
    val discount = integer("discount")
    val dateAdded = varchar(name = "dateAdded", length = 20)
    val prepareDuration = integer("prepareDuration")

    override val primaryKey = PrimaryKey(id, name = "PK_RESULT_ID")
}