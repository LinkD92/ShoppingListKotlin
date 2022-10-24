package com.symbol.shoppinglist.feature_category.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.symbol.shoppinglist.R

@Entity(tableName = "categories")
data class Category(
    val name: String,
    var color: Long = 0,
    var isExpanded: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

