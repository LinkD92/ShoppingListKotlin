package com.symbol.shoppinglist.feature_product.presentation.display_products

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.symbol.shoppinglist.core.domain.util.OrderType
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.use_case.CategoryUseCases
import com.symbol.shoppinglist.feature_category.domain.util.CategoryOrder
import com.symbol.shoppinglist.feature_product.domain.model.Product
import com.symbol.shoppinglist.feature_product.domain.use_case.ProductUseCases
import com.symbol.shoppinglist.feature_product.domain.util.ProductOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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

    private val _products = mutableStateOf<List<Product>>(emptyList())
    val products: State<List<Product>> = _products


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
                    _products.value = productUseCases.getCategoryProducts(event.category)
                    productUseCases.expandCategory(event.category.apply { isExpanded = !isExpanded })
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


//    val categories = repository.getAllCategories()
//
//    fun updateProduct(product: Product) {
//        viewModelScope.launch {
//            delay(50)
//            repository.updateProduct(product)
//        }
//    }
//
//    fun getCategoriesProduct(categoryId: Int): Flow<List<Product>> =
//        repository.getCategoryProducts(categoryId)
//
//    fun deleteProduct(product: Product) {
//        viewModelScope.launch {
//            repository.deleteProduct(product)
//        }
//    }
//
//    fun deleteProductById(productId: Int) {
//        viewModelScope.launch {
//            repository.deleteProductById(productId)
//        }
//    }
//
//    fun updateCategory(category: Category) {
//        viewModelScope.launch {
//            repository.updateCategory(category)
//        }
//    }
//
//    fun changeCategoryExpand(category: Category, isExpanded: Boolean) {
//        viewModelScope.launch {
//            repository.updateCategory(category.apply { this.isExpanded = isExpanded })
//        }
//    }
}

