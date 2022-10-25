package com.symbol.shoppinglist.feature_category.presentation.manage_categories

import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.util.CategoryOrder

sealed class ManageCategoriesEvent {
    data class Order(val categoryOrder: CategoryOrder): ManageCategoriesEvent()
    data class DeleteCategory(val category: Category): ManageCategoriesEvent()
    object RestoreCategory: ManageCategoriesEvent()
}