package com.symbol.shoppinglist.feature_product.presentation.display_products

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.use_case.CategoryUseCases
import com.symbol.shoppinglist.feature_category.domain.util.FullCategoryOrderType
import com.symbol.shoppinglist.feature_product.domain.model.Product
import com.symbol.shoppinglist.feature_product.domain.model.ProductPromptMessage
import com.symbol.shoppinglist.feature_product.domain.use_case.ProductUseCases
import com.symbol.shoppinglist.feature_product.presentation.display_products.components.CategoryCardMenuEvent
import com.symbol.shoppinglist.feature_settings.domain.use_case.SettingsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
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

    private val _testState =
            MutableStateFlow<Map<Category, Map<Product, StateFlow<Boolean>>>>(emptyMap())
    val testState = _testState.asStateFlow()

    private val _productsOfCategoryState =
            MutableStateFlow<Map<Category, List<StateFlow<Product>>>>(emptyMap())
    val productsOfCategoryState = _productsOfCategoryState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<ProductPromptMessage>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var recentlyDeletedProduct: Product? = null
    private var getCategoriesJob: Job? = null
    private var getSettingsJob: Job? = null

    private var changeProductsSelection: Job? = null
    private val listOfChangedProducts = mutableListOf<Product>()


    init {
        getSettings()
    }

    fun onEvent(event: DisplayProductsEvent) {
        when (event) {
            is DisplayProductsEvent.DeleteProduct -> {
                viewModelScope.launch {
                    recentlyDeletedProduct = productUseCases.getProduct(event.productId)
                    val prompt = productUseCases.deleteProduct(event.productId)
                    _eventFlow.emit(prompt)
                }
            }
            is DisplayProductsEvent.OnProductLongClick -> {
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
                    categoryUseCases.expandCategory(event.category.apply {
                        isExpanded = !isExpanded
                    })
                }
            }
            is DisplayProductsEvent.ChangeProductSelection -> {
                listOfChangedProducts.add(event.product.apply { isChecked = !isChecked })
                changeProductsSelection?.cancel()
                changeProductsSelection = viewModelScope.launch {
                    delay(1000)
                    productUseCases.insertProducts(listOfChangedProducts)
                    listOfChangedProducts.clear()
                }
            }
        }
    }

    fun onMenuEvent(event: CategoryCardMenuEvent) {
        when (event) {
            is CategoryCardMenuEvent.CheckUncheckAll -> {
//                val list = event.products.toList().toMutableList()
//                val isFirstProductChecked = list[0].second.value
//                val isLastProductChecked = list.last().second.value
                val isFirstProductChecked = event.products.first().value.isChecked
                val isLastProductChecked = event.products.last().value.isChecked
                val shouldCheckAllProducts = !(isFirstProductChecked && isLastProductChecked)
                val changedList = mutableListOf<Product>()
                event.products.forEach { product ->
                    changedList.add(product.value.apply { isChecked = shouldCheckAllProducts })
                }
                Log.d("QWAS - onMenuEvent:", "$changedList")
                viewModelScope.launch {
                    productUseCases.insertProducts(changedList)
                }
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
                getCategoriesProduct(categories)
            }
        }
    }

    private fun getSettings() {
        getSettingsJob?.cancel()
        getSettingsJob = viewModelScope.launch {
            settingsUseCases.getSettings().collect { settings ->
                _state.value = state.value.copy(
                    categoryOrderType = settings.fullCategoryOrderType
                )
                getCategories(settings.fullCategoryOrderType)
            }
        }
    }

    private fun getCategoriesProduct(categories: List<Category>) {
        categories.forEach { category ->
            viewModelScope.launch {
                productUseCases.getCategoryProducts(category.id).collect { products ->
                    _testState.value = _testState.value.toMutableMap().apply {
                        val productsMap = mutableMapOf<Product, StateFlow<Boolean>>()
                        products.onEach { product ->
                            productsMap[product] = MutableStateFlow(product.isChecked)
                        }
                        this[category] = productsMap
                    }
                    val productsState  = mutableListOf<StateFlow<Product>>()
                    products.forEach { product ->
                        productsState.add(MutableStateFlow(product))
                    }
                    _productsOfCategoryState.value = productsOfCategoryState.value.toMutableMap().apply {
                        this[category] = productsState
                    }
                }
            }
        }
    }

    fun getCategoriesProduct(categoryId: Int): Flow<List<Product>> =
            productUseCases.getCategoryProducts(categoryId)

    fun getProduct(productId: Int) = viewModelScope.launch {
        productUseCases.getProduct(productId)
    }
}
