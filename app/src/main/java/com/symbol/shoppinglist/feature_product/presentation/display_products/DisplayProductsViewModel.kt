package com.symbol.shoppinglist.feature_product.presentation.display_products

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.use_case.CategoryUseCases
import com.symbol.shoppinglist.feature_product.domain.model.Product
import com.symbol.shoppinglist.feature_product.domain.model.ProductPromptMessage
import com.symbol.shoppinglist.feature_product.domain.use_case.ProductUseCases
import com.symbol.shoppinglist.feature_settings.domain.use_case.SettingsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisplayProductsViewModel @Inject constructor(
    private val productUseCases: ProductUseCases,
    private val categoryUseCases: CategoryUseCases,
    private val settingsUseCases: SettingsUseCases
) :
    ViewModel() {

    private val _state = mutableStateOf(DisplayProductsState())
    val state: State<DisplayProductsState> = _state

    private var _products = emptyFlow<List<Product>>()
    val products = _products

    private val _eventFlow = MutableSharedFlow<ProductPromptMessage>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var recentlyDeletedProduct: Product? = null

    private val getCategoriesJob = viewModelScope.launch {
        categoryUseCases.getCategories(state.value.categoryOrder).collect { list ->
            _state.value = state.value.copy(
                categories = list
            )
        }
    }


    private val getPreferencesJob = viewModelScope.launch {
        settingsUseCases.getDisplayProductsCategoriesOrder()
    }


    init {
        viewModelScope.launch {
            getPreferencesJob.join()
            getCategoriesJob.join()
        }
    }

    fun onEvent(event: DisplayProductsEvent) {
        when (event) {
            is DisplayProductsEvent.Order -> {}
            is DisplayProductsEvent.DeleteProduct -> {
                viewModelScope.launch {
                    recentlyDeletedProduct = productUseCases.getProduct(event.productId)
                    val prompt = productUseCases.deleteProduct(event.productId)
                    _eventFlow.emit(prompt)
                }
            }
            is DisplayProductsEvent.OnProductLongClick -> {
                _state.value.longPressProduct = event.product
            }
            is DisplayProductsEvent.RestoreProduct -> {
                viewModelScope.launch {
                    val prompt =
                        productUseCases.insertProduct(recentlyDeletedProduct ?: return@launch)
                    _eventFlow.emit(prompt)
                    recentlyDeletedProduct = null
                }
            }
            is DisplayProductsEvent.ExpandCategory -> {
                viewModelScope.launch {
                    productUseCases.expandCategory(event.category.apply {
                        isExpanded = !isExpanded
                    })
                }
            }
            is DisplayProductsEvent.ChangeProductSelection -> {
                viewModelScope.launch {
                    productUseCases.insertProduct(
                        event.product.apply { isChecked = !isChecked },
                        event.product.name
                    )
                }
            }
        }
    }


    private fun getCategories(categoryOrder: String) {
        viewModelScope.launch {
            categoryUseCases.getCategories(categoryOrder).collect { categories ->
                _state.value =
                    state.value.copy(
                        categories = categories,
                        categoryOrder = categoryOrder
                    )
            }
        }
    }

    private fun getPreferences() {
        viewModelScope.launch {
            settingsUseCases.getDisplayProductsCategoriesOrder().collect { preferences ->
                Log.d("QWAS - getPreferences:", "prefs: $preferences")
                _state.value = state.value.copy(
                    categoryOrder = preferences
                )
            }
        }
    }

    fun reorder(categories: List<Category>) {
        _state.value = state.value.copy(
            categories = categories
        )
    }

    fun pushReorder(categories: List<Category>) {
        viewModelScope.launch { productUseCases.reorderCategories(categories) }

    }

    fun getCategoriesProduct(categoryId: Int): Flow<List<Product>> =
        productUseCases.getCategoryProducts(categoryId)

}
