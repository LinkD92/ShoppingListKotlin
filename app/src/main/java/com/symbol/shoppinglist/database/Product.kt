package com.symbol.shoppinglist.database

import androidx.room.Entity

@Entity(tableName = "products", primaryKeys =["name"])
data class Product (
    val name: String,
    val category: String?,
    val price: Int?
        ){
}