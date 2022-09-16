package com.example.tables

import org.jetbrains.exposed.sql.Table

object SellerOpenHourTable: Table() {
    val id = long("id").uniqueIndex().autoIncrement()
    val sellerId = long("seller_id") references SellerTable.id
    val saturday = integer("saturday")
    val sunday = integer("sunday")
    val monday = integer("monday")
    val tuesday = integer("tuesday")
    val wednesday = integer("wednesday")
    val thursday = integer("thursday")
    val friday = integer("friday")

    override val primaryKey = PrimaryKey(id, name = "PK_SELLER_OPEN_HOUR_ID")
}

object SellerCloseHourTable: Table() {
    val id = long("id").uniqueIndex().autoIncrement()
    val sellerId = long("seller_id") references SellerTable.id
    val saturday = integer("saturday")
    val sunday = integer("sunday")
    val monday = integer("monday")
    val tuesday = integer("tuesday")
    val wednesday = integer("wednesday")
    val thursday = integer("thursday")
    val friday = integer("friday")

    override val primaryKey = PrimaryKey(id, name = "PK_SELLER_CLOSE_HOUR_ID")
}