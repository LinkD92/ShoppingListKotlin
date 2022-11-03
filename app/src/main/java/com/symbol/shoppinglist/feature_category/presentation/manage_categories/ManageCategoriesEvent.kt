package com.symbol.shoppinglist.feature_category.presentation.manage_categories

import com.symbol.shoppinglist.feature_category.domain.model.Category

sealed class ManageCategoriesEvent {
    data class DeleteCategory(val category: Category): ManageCategoriesEvent()
    object RestoreCategory: ManageCategoriesEvent()
}