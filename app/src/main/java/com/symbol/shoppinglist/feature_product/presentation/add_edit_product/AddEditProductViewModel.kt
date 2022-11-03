package com.symbol.shoppinglist.feature_product.presentation.add_edit_product

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.symbol.shoppinglist.DispatcherProvider
import com.symbol.shoppinglist.core.data.util.NavigationRoutes
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.use_case.CategoryUseCases
import com.symbol.shoppinglist.feature_category.domain.util.CategoryOrderType
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
    private val dispatcher: DispatcherProvider
) :
    ViewModel() {

    private val invalidId = NavigationRoutes.Arguments.INVALID_ID
    private val dummyCategory = Category(name = "Select category", color = 4294967295)
    private var productNameValidator: String =""

    private val _productName = mutableStateOf("")
    val productName: State<String> = _productName

    private val _productCategory = mutableStateOf(dummyCategory)
    val productCategory: State<Category> = _productCategory

    private val _productQuantity = mutableStateOf("")
    val productQuantity: State<String> = _productQuantity

    private val _categories = mutableStateOf<List<Category>>(emptyList())
    val categories: State<List<Category>> = _categories

    private val _eventFlow = MutableSharedFlow<ProductPromptMessage>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var getCategoriesJob: Job? = null

    private val currentProductId =
        savedStateHandle.get<Int>(NavigationRoutes.Products.Arguments.ID)
            ?: invalidId

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
                            Product(productName.value, productCategory.value.id, true, 0)
                        } else {
                            Product(
                                productName.value,
                                productCategory.value.id,
                                true,
                                0,
                                currentProductId
                            )
                        },
                        productNameValidator
                    )
                    _eventFlow.emit(prompt)
                }
            }
            is AddEditProductEvent.EnteredName -> _productName.value = event.value
            is AddEditProductEvent.EnteredQuantity -> _productQuantity.value = event.value
            is AddEditProductEvent.ChooseCategory -> _productCategory.value = event.value
        }
    }

    private fun getProduct(id: Int) {
        if (id != invalidId) {
            viewModelScope.launch {
                val product = productUseCases.getProduct(id)
                _productCategory.value = categoryUseCases.getCategory(product.categoryId)
                _productName.value = product.name
            }
        }
    }

    private fun getCategories() {
        getCategoriesJob?.cancel()
        getCategoriesJob = categoryUseCases.getCategories(CategoryOrderType.NAME)
            .onEach { categories ->
                _categories.value = categories
            }
            .launchIn(viewModelScope)
    }
}