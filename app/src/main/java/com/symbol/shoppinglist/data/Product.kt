package com.symbol.shoppinglist.data

import androidx.room.Entity

@Entity(tableName = "products", primaryKeys = ["name"])
data class Product(
    val name: String,
    val category: String,
    val price: Int?,
    val isChecked: Boolean = false
) {
}