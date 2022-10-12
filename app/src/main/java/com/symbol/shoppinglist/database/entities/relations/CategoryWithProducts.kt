package com.symbol.shoppinglist.database.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.symbol.shoppinglist.database.entities.Category
import com.symbol.shoppinglist.database.entities.Product

data class CategoryWithProducts(
    @Embedded
    val category: Category,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val products: List<Product>
)