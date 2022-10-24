package com.symbol.shoppinglist.ui.productAdd

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.symbol.shoppinglist.DispatcherProvider
import com.symbol.shoppinglist.FieldValidation
import com.symbol.shoppinglist.NavigationRoutes
import com.symbol.shoppinglist.R
import com.symbol.shoppinglist.database.ListRepository
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_product.domain.model.Product
import com.symbol.shoppinglist.feature_product.domain.use_case.ProductUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias ProductAction = suspend () -> Unit

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val productUseCases: ProductUseCases,
    private val savedStateHandle: SavedStateHandle,
    private val dispatcher: DispatcherProvider
) :
    ViewModel() {







//    private val invalidId = NavigationRoutes.Arguments.INVALID_ID
//    private val dummyCategory = Category(name = "Select category", color = 4294967295)
//    private var receivedCategoryName = ""
//    private val productIdReceived =
//        savedStateHandle.get<Int>(NavigationRoutes.Products.Arguments.ID)
//            ?: invalidId
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
//    private fun getProduct(id: Int) {
//        if (id != invalidId) {
//            viewModelScope.launch(dispatcher.main) {
//                val product = repository.getProduct(id)
//                receivedCategoryName = product.name
//                productName = product.name
//                chooseCategory(repository.getCategory(product.categoryId))
//            }
//        }
//    }
//
//    private fun getAction(): ProductAction {
//        return if (productIdReceived == invalidId) ::addProduct else ::updateProduct
//    }
}