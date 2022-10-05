package com.symbol.shoppinglist.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = false)
    val productName: String,
    val isProductChecked: Boolean = false,
    var categoryName: String
) {
    var productPrice: Int = 0
}