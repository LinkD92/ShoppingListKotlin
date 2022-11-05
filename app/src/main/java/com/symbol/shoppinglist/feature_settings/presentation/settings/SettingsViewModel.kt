package com.symbol.shoppinglist.feature_settings.presentation.settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.symbol.shoppinglist.feature_category.domain.use_case.CategoryUseCases
import com.symbol.shoppinglist.feature_category.domain.util.FullCategoryOrderType
import com.symbol.shoppinglist.feature_product.domain.use_case.ProductUseCases
import com.symbol.shoppinglist.feature_settings.domain.use_case.SettingsUseCases
import com.symbol.shoppinglist.feature_settings.presentation.display_products.SettingsDisplayProductEvent
import com.symbol.shoppinglist.feature_settings.presentation.display_products.SettingsDisplayProductsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val productUseCases: ProductUseCases,
    private val categoryUseCases: CategoryUseCases,
    private val settingsUseCases: SettingsUseCases
) : ViewModel() {

    private val _state = mutableStateOf(SettingsDisplayProductsState())
    val state: State<SettingsDisplayProductsState> = _state

    private var getCategoriesJob: Job? = null
    private var getSettingsJob: Job? = null

    init {
        getSettings()
    }

    fun onEvent(event: SettingsDisplayProductEvent) {
        when (event) {
            is SettingsDisplayProductEvent.ChangeSortType -> {
                viewModelScope.launch {
                    val categoryOrderType = state.value.categoryOrderType
                    val fullCategoryOrderType =
                        FullCategoryOrderType(categoryOrderType, event.sortType)
                    settingsUseCases.saveDisplayProductsCategoriesOrder(fullCategoryOrderType)
                }
            }
            is SettingsDisplayProductEvent.ChangeOrderType -> {
                viewModelScope.launch {
                    val sortType = state.value.sortType
                    val fullCategoryOrderType =
                        FullCategoryOrderType(event.categoryOrderType, sortType)
                    settingsUseCases.saveDisplayProductsCategoriesOrder(fullCategoryOrderType)
                }
            }
            is SettingsDisplayProductEvent.SaveCustomOrderSettings -> {
                viewModelScope.launch {
                    categoryUseCases.reorderCategories(event.categories.apply {
                        forEachIndexed { index, category ->
                            category.customOrder = index
                        }
                    })
                }
            }
        }
    }

    private fun getSettings() {
        getSettingsJob?.cancel()
        getSettingsJob = viewModelScope.launch {
            settingsUseCases.getSettings().collect { settings ->
                _state.value = state.value.copy(
                    sortType = settings.fullCategoryOrderType.sortType,
                    categoryOrderType = settings.fullCategoryOrderType.categoryOrderType
                )
                getCategories(settings.fullCategoryOrderType)
            }
        }
    }

    private fun getCategories(fullCategoryOrderType: FullCategoryOrderType) {
        getCategoriesJob?.cancel()
        getCategoriesJob = viewModelScope.launch {
            categoryUseCases.getCategories(fullCategoryOrderType).collect { categories ->
                _state.value =
                    state.value.copy(
                        categories = categories,
                    )
            }
        }
    }
}