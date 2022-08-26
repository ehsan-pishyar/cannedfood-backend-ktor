package com.example.tables

import org.jetbrains.exposed.sql.Table

object SellerOpenStatusTable: Table() {
    val id = integer("id").uniqueIndex()
    val sellerId = long("seller_id") references SellerTable.id
    val isOpen = bool("is_open").default(false)

    override val primaryKey = PrimaryKey(id, name = "PK_IS_OPEN_ID")
}