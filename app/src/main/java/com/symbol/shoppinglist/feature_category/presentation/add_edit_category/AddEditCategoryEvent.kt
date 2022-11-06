package com.symbol.shoppinglist.feature_category.presentation.add_edit_category

sealed class AddEditCategoryEvent {
    data class EnteredName(val value: String): AddEditCategoryEvent()
    data class ChangeColor(val value: Long): AddEditCategoryEvent()
    object SaveCategory: AddEditCategoryEvent()
}