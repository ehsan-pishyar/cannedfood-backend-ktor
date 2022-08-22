package com.example.tables

import com.example.models.RatingStars
import org.jetbrains.exposed.sql.Table

object SellerRatingTable: Table() {
    val id = integer("id").uniqueIndex().autoIncrement()
    val fromCustomerId = long("from_customer_id") references CustomerTable.id
    val rating = double("rating_stars")
    val toSellerId = integer("to_seller_id") references SellerTable.id

    override val primaryKey = PrimaryKey(id, name = "PK_SELLER_RATING_ID")
}

object ResultRatingTable: Table() {
    val id = integer("id").uniqueIndex().autoIncrement()
    val fromCustomerId = long("from_customer_id") references CustomerTable.id
    val rating = double("rating_stars")
    val toResultId = integer("to_result_id") references ResultsTable.id

    override val primaryKey = PrimaryKey(id, name = "PK_RESULT_RATING_ID")
}

object SellerCommentTable: Table() {
    val id = integer("id").uniqueIndex().autoIncrement()
    val fromCustomerId = long("from_customer_id") references CustomerTable.id
    val message = text(name = "message")
    val toSellerId = integer("to_seller_id") references SellerTable.id

    override val primaryKey = PrimaryKey(id, name = "PK_SELLER_COMMENT_ID")
}

object ResultCommentTable: Table() {
    val id = integer("id").uniqueIndex().autoIncrement()
    val fromCustomerId = long("from_customer_id") references CustomerTable.id
    val message = text(name = "message")
    val toResultId = integer("to_result_id") references ResultsTable.id

    override val primaryKey = PrimaryKey(id, name = "PK_RESULT_COMMENT_ID")
}