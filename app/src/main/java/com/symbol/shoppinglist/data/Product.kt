package com.symbol.shoppinglist.data

import androidx.room.Entity

@Entity(tableName = "products", primaryKeys = ["name"])
data class Product(
    val name: String,
    val isChecked: Boolean = false
) {
    var category: String = ""
    var price: Int = 0
}