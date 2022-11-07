package com.symbol.shoppinglist.feature_settings.presentation.display_products

import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.util.CategoryOrderType
import com.symbol.shoppinglist.feature_category.domain.util.SortType

data class SettingsDisplayProductsState(
    val sortType: SortType = SortType.ASCENDING,
    val categoryOrderType: CategoryOrderType = CategoryOrderType.NAME,
    val categories: List<Category> = emptyList()
)
