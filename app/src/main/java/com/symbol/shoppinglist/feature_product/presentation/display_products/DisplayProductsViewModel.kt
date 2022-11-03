package com.symbol.shoppinglist.feature_product.presentation.display_products

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.symbol.shoppinglist.feature_category.domain.use_case.CategoryUseCases
import com.symbol.shoppinglist.feature_category.domain.util.CategoryOrderType
import com.symbol.shoppinglist.feature_product.domain.model.Product
import com.symbol.shoppinglist.feature_product.domain.model.ProductPromptMessage
import com.symbol.shoppinglist.feature_product.domain.use_case.ProductUseCases
import com.symbol.shoppinglist.feature_settings.domain.model.AppSettings
import com.symbol.shoppinglist.feature_settings.domain.use_case.SettingsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    private val _appSettings = mutableStateOf(AppSettings())
    val appSettings: State<AppSettings> = _appSettings

    private val _eventFlow = MutableSharedFlow<ProductPromptMessage>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var recentlyDeletedProduct: Product? = null

    private var getCategoriesJob: Job? = null

    private var getSettingsJob: Job? = null

    init {
        getSettings()
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


    private fun getCategories(categoryOrder: CategoryOrderType) {
        getCategoriesJob?.cancel()
        getCategoriesJob = viewModelScope.launch {
            categoryUseCases.getCategories(categoryOrder).collect { categories ->
                _state.value =
                    state.value.copy(
                        categories = categories,
                    )
            }
        }
    }

    private fun getSettings() {
        getSettingsJob?.cancel()
        getSettingsJob = viewModelScope.launch {
            settingsUseCases.getSettings().collect { settings ->
                _state.value = state.value.copy(
                    categoryOrderType = settings.categoryOrderType
                )
                getCategories(settings.categoryOrderType)
            }
        }
    }

    fun getCategoriesProduct(categoryId: Int): Flow<List<Product>> =
        productUseCases.getCategoryProducts(categoryId)

}
