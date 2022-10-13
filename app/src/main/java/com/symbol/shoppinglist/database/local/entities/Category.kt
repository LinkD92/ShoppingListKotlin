package com.symbol.shoppinglist.database.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    var name: String = "",
    var color: Long = 0,
    var isExpanded: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)
