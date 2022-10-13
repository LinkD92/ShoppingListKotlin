package com.symbol.shoppinglist.database.local.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.symbol.shoppinglist.database.local.entities.Category
import com.symbol.shoppinglist.database.local.entities.Product

data class CategoryWithProducts(
    @Embedded
    val category: Category,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val products: List<Product>
)