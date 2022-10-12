package com.symbol.shoppinglist.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val categoryName: String = "",
    val categoryColor: Long = 0,
){
    var isExpanded: Boolean = false
}