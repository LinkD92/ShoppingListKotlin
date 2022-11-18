package com.symbol.shoppinglist.feature_product.presentation.display_products

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

    private val _categoriesState = MutableStateFlow<List<Category>>(emptyList())
    val categoriesState = _categoriesState.asStateFlow()

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
                changeProductsSelection =
                    viewModelScope.launch {
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
                val isFirstProductChecked = event.products.first().isChecked
                val isLastProductChecked = event.products.last().isChecked
                val shouldCheckAllProducts = !(isFirstProductChecked && isLastProductChecked)
                val changedList = mutableListOf<Product>()
                event.products.forEach { product ->
                    changedList.add(product.apply { isChecked = shouldCheckAllProducts })
                }
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
                _categoriesState.value = categories
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

    fun getCategoriesProduct(categoryId: Int): Flow<List<Product>> =
        productUseCases.getCategoryProducts(categoryId)

    fun getProduct(productId: Int) = viewModelScope.launch {
        productUseCases.getProduct(productId)
    }
}
