package com.symbol.shoppinglist.feature_category.presentation.add_edit_category

import com.symbol.shoppinglist.feature_category.domain.model.Category

sealed class AddEditCategoryEvent {
    data class EnteredName(val value: String): AddEditCategoryEvent()
    data class ChangeColor(val value: Long): AddEditCategoryEvent()
    object SaveCategory: AddEditCategoryEvent()
//    data class RestoreCategory(val category: Category): AddEditCategoryEvent()
}