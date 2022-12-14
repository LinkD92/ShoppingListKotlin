package com.symbol.shoppinglist.feature_settings.presentation.settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.symbol.shoppinglist.feature_category.domain.use_case.CategoryUseCases
import com.symbol.shoppinglist.feature_category.domain.util.CategoryOrderType
import com.symbol.shoppinglist.feature_category.domain.util.FullCategoryOrderType
import com.symbol.shoppinglist.feature_category.domain.util.SortType
import com.symbol.shoppinglist.feature_product.domain.use_case.ProductUseCases
import com.symbol.shoppinglist.feature_settings.domain.use_case.SettingsUseCases
import com.symbol.shoppinglist.feature_settings.presentation.display_products.SettingsDisplayProductEvent
import com.symbol.shoppinglist.feature_settings.presentation.display_products.SettingsDisplayProductsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val productUseCases: ProductUseCases,
    private val categoryUseCases: CategoryUseCases,
    private val settingsUseCases: SettingsUseCases
) : ViewModel() {

    private val _stateSettingsDisplayProducts = mutableStateOf(SettingsDisplayProductsState())
    val stateSettingsDisplayProduct: State<SettingsDisplayProductsState> = _stateSettingsDisplayProducts

    private var getCategoriesJob: Job? = null
    private var getSettingsJob: Job? = null

    init {
        getSettings()
    }

    fun onEvent(event: SettingsDisplayProductEvent) {
        when (event) {
            is SettingsDisplayProductEvent.ChangeSortType -> {
                viewModelScope.launch {
                    val categoryOrderType = stateSettingsDisplayProduct.value.categoryOrderType
                    val fullCategoryOrderType =
                            FullCategoryOrderType(categoryOrderType, event.sortType)
                    settingsUseCases.saveDisplayProductsCategoriesOrder(fullCategoryOrderType)
                }
            }
            is SettingsDisplayProductEvent.ChangeOrderType -> {
                viewModelScope.launch {
                    val sortType = stateSettingsDisplayProduct.value.sortType
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
                _stateSettingsDisplayProducts.value = stateSettingsDisplayProduct.value.copy(
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
                _stateSettingsDisplayProducts.value =
                        stateSettingsDisplayProduct.value.copy(
                            categories = categories,
                        )
            }
        }
    }


    // TODO: 08/11/2022 needs better way to implement this
    fun secondaryTextBuilder(): String{
        val sortType = stateSettingsDisplayProduct.value.sortType
        val orderType = stateSettingsDisplayProduct.value.categoryOrderType
        return if(sortType != SortType.CUSTOM)
            "${sortType.name.capitalized()} ${orderType.name.capitalized()}"
        else sortType.name.capitalized()

    }

    // TODO: 08/11/2022 needs better way to implement this
    private fun String.capitalized(): String {
        return this.lowercase().replaceFirstChar {
            if (it.isLowerCase())
                it.titlecase(Locale.getDefault())
            else it.toString()
        }
    }
}

