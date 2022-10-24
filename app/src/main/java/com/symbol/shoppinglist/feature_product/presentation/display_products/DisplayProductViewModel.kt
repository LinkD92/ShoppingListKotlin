package com.symbol.shoppinglist.feature_product.presentation.display_products

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.symbol.shoppinglist.core.domain.util.OrderType
import com.symbol.shoppinglist.feature_category.domain.use_case.CategoryUseCases
import com.symbol.shoppinglist.feature_category.domain.util.CategoryOrder
import com.symbol.shoppinglist.feature_product.domain.model.Product
import com.symbol.shoppinglist.feature_product.domain.use_case.ProductUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisplayProductViewModel @Inject constructor(
    private val productUseCases: ProductUseCases,
    private val categoryUseCases: CategoryUseCases
) :
    ViewModel() {

    private val _state = mutableStateOf(DisplayProductsState())
    val state: State<DisplayProductsState> = _state

    private var _products = emptyFlow<List<Product>>()
    val products = _products


    private var recentlyDeletedProduct: Product? = null

    private var getCategoriesJob: Job? = null

    init {
        getCategories(CategoryOrder.Name(OrderType.Ascending))
    }

    fun onEvent(event: DisplayProductsEvent) {
        when (event) {
            is DisplayProductsEvent.Order -> {}
            is DisplayProductsEvent.DeleteProduct -> {
                viewModelScope.launch {
                    recentlyDeletedProduct = productUseCases.getProduct(event.productId)
                    productUseCases.deleteProduct(event.productId)
                }
            }
            is DisplayProductsEvent.EditProduct -> {

            }
            is DisplayProductsEvent.RestoreProduct -> {
                viewModelScope.launch {
                    productUseCases.insertProduct(recentlyDeletedProduct ?: return@launch)
                    recentlyDeletedProduct = null
                }
            }
            is DisplayProductsEvent.CreateProduct -> {}
            is DisplayProductsEvent.ExpandCategory -> {
                viewModelScope.launch {
                    productUseCases.expandCategory(event.category.apply {
                        isExpanded = !isExpanded
                    })
                }
            }
            is DisplayProductsEvent.ChangeProductSelection -> {
                viewModelScope.launch {
                    productUseCases.insertProduct(event.product.apply { isChecked = !isChecked })
                }
            }
        }
    }

    private fun getCategories(categoryOrder: CategoryOrder) {
        getCategoriesJob?.cancel()
        getCategoriesJob = categoryUseCases.getCategories(categoryOrder)
            .onEach { categories ->
                _state.value =
                    state.value.copy(
                        categories = categories,
                        categoryOrder = categoryOrder
                    )
            }
            .launchIn(viewModelScope)
    }

    // TODO: 24/10/2022
    fun getCategoriesProduct(categoryId: Int): Flow<List<Product>> =
        productUseCases.getCategoryProducts(categoryId)
}

