package com.symbol.shoppinglist.feature_product.presentation.display_products

import com.symbol.shoppinglist.core.domain.util.OrderType
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.util.CategoryOrderType
import com.symbol.shoppinglist.feature_category.domain.util.FullCategoryOrderType
import com.symbol.shoppinglist.feature_category.domain.util.SortType
import com.symbol.shoppinglist.feature_product.domain.model.ProductOrder

// TODO: 18/11/2022 to verify the approach of state
data class DisplayProductsState (
    val categories: List<Category> = emptyList(),
    val categoryOrderType: FullCategoryOrderType = FullCategoryOrderType(
        CategoryOrderType.NAME,
        SortType.ASCENDING
    ),
    val productOrder: ProductOrder = ProductOrder.Name(OrderType.Ascending),
)