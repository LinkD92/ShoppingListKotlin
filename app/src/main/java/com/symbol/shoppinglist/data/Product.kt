package com.symbol.shoppinglist.product

import androidx.room.Entity
import com.symbol.shoppinglist.category.Category

@Entity(tableName = "products", primaryKeys =["name"])
data class Product (
    val name: String,
    val category: String,
    val price: Int?
        ){
}