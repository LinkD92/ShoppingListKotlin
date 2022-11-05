package com.symbol.shoppinglist.feature_settings.presentation.display_products

import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.util.CategoryOrderType
import com.symbol.shoppinglist.feature_category.domain.util.SortType

sealed class SettingsDisplayProductEvent {
    data class ChangeSortType(val sortType: SortType) : SettingsDisplayProductEvent()
    data class ChangeOrderType(val categoryOrderType: CategoryOrderType) :
        SettingsDisplayProductEvent()
    data class SaveCustomOrderSettings(val categories: List<Category>) :
        SettingsDisplayProductEvent()
}