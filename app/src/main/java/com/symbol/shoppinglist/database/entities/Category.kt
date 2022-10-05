package com.symbol.shoppinglist.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = false)
    val categoryName: String,
    val categoryColor: String = ""
)