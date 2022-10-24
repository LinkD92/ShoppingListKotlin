package com.symbol.shoppinglist.feature_product.presentation.display_products

import androidx.compose.runtime.State
import com.symbol.shoppinglist.core.domain.util.OrderType
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.util.CategoryOrder
import com.symbol.shoppinglist.feature_product.domain.util.ProductOrder

data class DisplayProductsState(
    val categories: List<Category> = emptyList(),
    val categoryOrder: CategoryOrder = CategoryOrder.Name(OrderType.Ascending),
    val productOrder: ProductOrder = ProductOrder.Name(OrderType.Ascending),
)