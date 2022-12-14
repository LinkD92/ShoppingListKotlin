package com.symbol.shoppinglist.feature_category.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "categories")
data class Category(
    val name: String,
    var color: Long = 0,
    var isExpanded: Boolean = false,
    var customOrder: Int = 0,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

