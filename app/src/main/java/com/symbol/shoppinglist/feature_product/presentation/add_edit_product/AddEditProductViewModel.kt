package com.symbol.shoppinglist.feature_product.presentation.add_edit_product

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.symbol.shoppinglist.DispatcherProvider
import com.symbol.shoppinglist.NavigationRoutes
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.use_case.CategoryUseCases
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
        Log.d("QWAS - :", "$currentProductId")
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
                        }
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
                _productCategory.value = dummyCategory
                _productName.value = product.name
            }
        }
    }

    private fun getCategories() {
        getCategoriesJob?.cancel()
        getCategoriesJob = categoryUseCases.getCategories()
            .onEach { categories ->
                _categories.value = categories
            }
            .launchIn(viewModelScope)
    }


//    private var receivedCategoryName = ""

//    private val _successObserver = MutableSharedFlow<Int>()
//    val successObserver = _successObserver.asSharedFlow()
//    val allProducts = repository.getAllProducts()
//    val allCategories = repository.getAllCategories()
//    var productName by mutableStateOf("")
//        private set
//    var productCategory by mutableStateOf(dummyCategory)
//        private set
//    var productQuantity by mutableStateOf(1)
//        private set
//
//    init {
//        getProduct(productIdReceived)
//    }
//
//    fun confirmButtonClick() = viewModelScope.launch {
//        val validationResult = validateFields()
//        if (validationResult == 0) {
//            getAction().invoke()
//        } else {
//            _successObserver.emit(validationResult)
//        }
//    }
//
//    fun updateName(input: String) {
//        productName = input
//    }
//
//    fun chooseCategory(input: Category) {
//        productCategory = input
//    }
//
//    fun updateQuantity(input: String) {
//        productQuantity = input.toInt()
//    }
//
//    private suspend fun addProduct() {
//        val product = Product(name = productName, categoryId = productCategory.id)
//        _successObserver.emit(R.string.product_added)
//        repository.addProduct(product)
//    }
//
//    private suspend fun updateProduct() {
//        val product = Product(productName, productCategory.id, id = productIdReceived)
//        _successObserver.emit(R.string.product_updated)
//        repository.updateProduct(product)
//    }
//
//    private suspend fun validateProduct(name: String): Boolean {
//        val count = repository.doesProductExists(name)
//        val duplicateValidation = count <= 0
//        val currentNameValidation = name == receivedCategoryName
//        return duplicateValidation || currentNameValidation
//    }
//
//    private suspend fun validateFields(): Int {
//        if (productName.length < FieldValidation.MIN_NAME_LENGTH
//            || productName.length > FieldValidation.MAX_NAME_LENGTH
//        ) {
//            return R.string.name_invalid
//        }
//        if (productCategory == dummyCategory) {
//            return R.string.category_invalid
//        }
//        if (!validateProduct(productName)) {
//            return R.string.name_exsists
//        }
//        return 0
//    }
//

//
//    private fun getAction(): ProductAction {
//        return if (productIdReceived == invalidId) ::addProduct else ::updateProduct
//    }
}