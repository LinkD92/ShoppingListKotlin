package com.symbol.shoppinglist.feature_product.presentation.add_edit_product

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.symbol.shoppinglist.core.data.util.NavigationRoutes
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.use_case.CategoryUseCases
import com.symbol.shoppinglist.feature_category.domain.util.CategoryOrderType
import com.symbol.shoppinglist.feature_category.domain.util.FullCategoryOrderType
import com.symbol.shoppinglist.feature_category.domain.util.SortType
import com.symbol.shoppinglist.feature_product.domain.model.Product
import com.symbol.shoppinglist.feature_product.domain.model.ProductPromptMessage
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
class AddEditProductViewModel @Inject constructor(
    private val productUseCases: ProductUseCases,
    private val categoryUseCases: CategoryUseCases,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val invalidId = NavigationRoutes.Arguments.INVALID_ID
    private val dummyCategory = Category(
        name = "Select category", color = 4294967295
    )
    private var productNameValidator: String = ""

    private val _productNameState = mutableStateOf("")
    val productNameState: State<String> = _productNameState

    private val _productCategoryState = mutableStateOf(dummyCategory)
    val productCategoryState: State<Category> = _productCategoryState

    private val _productQuantityState = mutableStateOf("")
    val productQuantityState: State<String> = _productQuantityState

    private val _categoriesState = mutableStateOf<List<Category>>(emptyList())
    val categoriesState: State<List<Category>> = _categoriesState

    private val _eventFlow = MutableSharedFlow<ProductPromptMessage>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var getCategoriesJob: Job? = null

    private val currentProductId =
        savedStateHandle.get<Int>(NavigationRoutes.Products.Arguments.ID) ?: invalidId

    init {
        getProduct(currentProductId)
        getCategories()
    }

    fun onEvent(event: AddEditProductEvent) {
        when (event) {
            is AddEditProductEvent.SaveProduct -> {
                viewModelScope.launch {
                    val prompt = productUseCases.insertProduct(
                        if (currentProductId == invalidId) {
                            Product(
                                productNameState.value, productCategoryState.value.id, true, 0
                            )
                        } else {
                            Product(
                                productNameState.value,
                                productCategoryState.value.id,
                                true,
                                0,
                                currentProductId
                            )
                        }, productNameValidator
                    )
                    _eventFlow.emit(prompt)
                }
            }
            is AddEditProductEvent.EnteredName -> _productNameState.value = event.value
            is AddEditProductEvent.EnteredQuantity -> _productQuantityState.value = event.value
            is AddEditProductEvent.ChooseCategory -> _productCategoryState.value = event.value
        }
    }

    private fun getProduct(id: Int) {
        if (id != invalidId) {
            viewModelScope.launch {
                val product = productUseCases.getProduct(id)
                _productCategoryState.value = categoryUseCases.getCategory(product.categoryId)
                _productNameState.value = product.name
            }
        }
    }

    private fun getCategories() {
        getCategoriesJob?.cancel()
        getCategoriesJob = categoryUseCases.getCategories(
            FullCategoryOrderType(CategoryOrderType.NAME, SortType.ASCENDING)
        ).onEach { categories -> _categoriesState.value = categories }.launchIn(viewModelScope)
    }
}