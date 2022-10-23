package com.symbol.shoppinglist.feature_product.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    val name: String,
    val categoryId: Int,
    var isChecked: Boolean = false,
    var price: Int = 0,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    )