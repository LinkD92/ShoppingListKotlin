package com.symbol.shoppinglist.feature_category.domain.util

import com.symbol.shoppinglist.feature_category.domain.model.Category
import kotlinx.serialization.Serializable


enum class CategoryOrderType() {
    NAME,
}

enum class SortType {
    ASCENDING,
    DESCENDING,
    CUSTOM
}

@Serializable
data class CategoryExpandStatus(val category: Category, val isExpanded: Boolean)

@Serializable
data class FullCategoryOrderType(
    val categoryOrderType: CategoryOrderType = CategoryOrderType.NAME,
    val sortType: SortType
)