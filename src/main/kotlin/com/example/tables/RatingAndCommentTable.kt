package com.example.tables

import org.jetbrains.exposed.sql.Table

object SellerRatingTable: Table() {
    val id = long("id").uniqueIndex().autoIncrement()
    val fromCustomerId = long("from_customer_id") references CustomerTable.id
    val rating = integer("rating_stars")
    val toSellerId = long("to_seller_id") references SellerTable.id

    override val primaryKey = PrimaryKey(id, name = "PK_SELLER_RATING_ID")
}

object ResultRatingTable: Table() {
    val id = long("id").uniqueIndex().autoIncrement()
    val fromCustomerId = long("from_customer_id") references CustomerTable.id
    val rating = integer("rating_stars")
    val toResultId = long("to_result_id") references ResultsTable.id

    override val primaryKey = PrimaryKey(id, name = "PK_RESULT_RATING_ID")
}

object SellerCommentTable: Table() {
    val id = long("id").uniqueIndex()
    val fromCustomerId = long("from_customer_id") references CustomerTable.id
    val message = text(name = "message")
    val toSellerId = long("to_seller_id") references SellerTable.id

    override val primaryKey = PrimaryKey(id, name = "PK_SELLER_COMMENT_ID")
}

object ResultCommentTable: Table() {
    val id = long("id").uniqueIndex()
    val fromCustomerId = long("from_customer_id") references CustomerTable.id
    val message = text(name = "message")
    val toResultId = long("to_result_id") references ResultsTable.id

    override val primaryKey = PrimaryKey(id, name = "PK_RESULT_COMMENT_ID")
}