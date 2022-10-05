package com.symbol.shoppinglist.database.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.symbol.shoppinglist.database.entities.Category
import com.symbol.shoppinglist.database.entities.Product

data class CategoryWithProducts(
    @Embedded
    val category: Category,
    @Relation(
        parentColumn = "categoryName",
        entityColumn = "categoryName"
    )
    val products: List<Product>
)