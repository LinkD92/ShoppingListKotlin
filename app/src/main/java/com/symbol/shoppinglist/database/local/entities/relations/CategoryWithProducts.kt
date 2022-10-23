package com.symbol.shoppinglist.database.local.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_product.domain.model.Product

data class CategoryWithProducts(
    @Embedded
    val category: Category,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val products: List<Product>
)