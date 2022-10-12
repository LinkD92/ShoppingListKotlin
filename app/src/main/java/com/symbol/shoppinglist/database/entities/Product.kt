package com.symbol.shoppinglist.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int =0,
    val productName: String,
    val categoryId: Int,

    ) {
    var isProductChecked: Boolean = false
    var productPrice: Int = 0
}