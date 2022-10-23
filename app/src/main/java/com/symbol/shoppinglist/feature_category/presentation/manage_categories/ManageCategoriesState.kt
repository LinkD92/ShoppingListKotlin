package com.symbol.shoppinglist.feature_category.presentation.manage_categories

import com.symbol.shoppinglist.core.domain.util.OrderType
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.util.CategoryOrder

data class ManageCategoriesState (
    val categories: List<Category> = emptyList(),
    val categoryOrder: CategoryOrder = CategoryOrder.Name(OrderType.Ascending)
)