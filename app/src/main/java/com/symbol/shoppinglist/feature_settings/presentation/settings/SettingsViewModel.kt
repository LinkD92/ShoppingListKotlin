package com.symbol.shoppinglist.feature_settings.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.symbol.shoppinglist.feature_category.domain.use_case.CategoryUseCases
import com.symbol.shoppinglist.feature_category.domain.util.CategoryOrderType
import com.symbol.shoppinglist.feature_category.domain.util.FullCategoryOrderType
import com.symbol.shoppinglist.feature_category.domain.util.SortType
import com.symbol.shoppinglist.feature_product.domain.use_case.ProductUseCases
import com.symbol.shoppinglist.feature_settings.domain.use_case.SettingsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val productUseCases: ProductUseCases,
    private val categoryUseCases: CategoryUseCases,
    private val settingsUseCases: SettingsUseCases
) : ViewModel() {

    fun changeCategoryOrderType() {
        viewModelScope.launch {
            val fullCategoryOrderType = FullCategoryOrderType(CategoryOrderType.NAME, SortType.ASCENDING)
            settingsUseCases.saveDisplayProductsCategoriesOrder(fullCategoryOrderType)
        }
    }


    fun changeCategoryOrderType1() {
        viewModelScope.launch {
            val fullCategoryOrderType = FullCategoryOrderType(CategoryOrderType.NAME, SortType.DESCENDING)
            settingsUseCases.saveDisplayProductsCategoriesOrder(fullCategoryOrderType)
        }
    }
}