package com.symbol.shoppinglist.feature_category.presentation.manage_categories

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.model.CategoryPromptMessage
import com.symbol.shoppinglist.feature_category.domain.use_case.CategoryUseCases
import com.symbol.shoppinglist.feature_category.domain.util.CategoryOrderType
import com.symbol.shoppinglist.feature_product.domain.model.Product
import com.symbol.shoppinglist.feature_product.domain.use_case.ProductUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageCategoriesViewModel @Inject constructor(
    private val categoryUseCases: CategoryUseCases,
    private val productUseCases: ProductUseCases
    ) :
    ViewModel() {

    private val _state = mutableStateOf(ManageCategoriesState())
    val state: State<ManageCategoriesState> = _state

    private val _eventFlow = MutableSharedFlow<CategoryPromptMessage>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var recentlyDeletedCategory: Category? = null
    private var recentlyRemovedProducts: List<Product>? = null

    private var getCategoriesJob: Job? = null
    private var getCategoryProductsJob: Job? = null

    init {
        getCategories(CategoryOrderType.NAME)
    }

    fun onEvent(event: ManageCategoriesEvent) {
        when (event) {
            is ManageCategoriesEvent.DeleteCategory -> {
                viewModelScope.launch {
                    val prompt = categoryUseCases.deleteCategory(event.category)
                    getCategoryProducts(event.category.id)
                    recentlyDeletedCategory = event.category
                    _eventFlow.emit(prompt)
                }
            }
            is ManageCategoriesEvent.RestoreCategory -> {
                viewModelScope.launch {
                    val prompt =
                        categoryUseCases.insertCategory(recentlyDeletedCategory ?: return@launch)
                    productUseCases.insertProducts(recentlyRemovedProducts ?: return@launch)
                    recentlyDeletedCategory = null
                    _eventFlow.emit(prompt)
                }
            }
        }
    }

    private fun getCategories(categoryOrderType: CategoryOrderType) {
        getCategoriesJob?.cancel()
        getCategoriesJob = categoryUseCases.getCategories(categoryOrderType)
            .onEach { categories ->
                _state.value =
                    state.value.copy(
                        categories = categories,
                        categoryOrderType = categoryOrderType
                    )
            }
            .launchIn(viewModelScope)
    }

    private fun getCategoryProducts(categoryId: Int) {
        getCategoryProductsJob?.cancel()
        getCategoryProductsJob = productUseCases.getCategoryProducts(categoryId)
            .onEach { products ->
                recentlyRemovedProducts = products
            }
            .launchIn(viewModelScope)
    }
}