package com.symbol.shoppinglist.feature_category.presentation.manage_categories

import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.util.CategoryOrderType

data class ManageCategoriesState (
    val categories: List<Category> = emptyList(),
    val categoryOrderType: CategoryOrderType = CategoryOrderType.NAME
)