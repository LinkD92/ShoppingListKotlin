package com.symbol.shoppinglist.feature_category.presentation.manage_categories

import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.util.CategoryOrderType
import com.symbol.shoppinglist.feature_category.domain.util.FullCategoryOrderType
import com.symbol.shoppinglist.feature_category.domain.util.SortType

data class ManageCategoriesState(
    val categories: List<Category> = emptyList(),
    val fullCategoryOrderType: FullCategoryOrderType =
        FullCategoryOrderType(
            CategoryOrderType.NAME,
            SortType.ASCENDING
        )
)