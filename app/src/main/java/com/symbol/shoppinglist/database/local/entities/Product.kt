package com.symbol.shoppinglist.database.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    var name: String,
    var categoryId: Int,
    var isChecked: Boolean = false,
    var price: Int = 0,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    )