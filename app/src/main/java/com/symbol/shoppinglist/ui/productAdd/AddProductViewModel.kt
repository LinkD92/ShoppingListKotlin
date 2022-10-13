package com.symbol.shoppinglist.ui.productAdd

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.symbol.shoppinglist.NavigationRoutes
import com.symbol.shoppinglist.R
import com.symbol.shoppinglist.database.ListRepository
import com.symbol.shoppinglist.database.local.entities.Category
import com.symbol.shoppinglist.database.local.entities.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val repository: ListRepository,
    private val savedStateHandle: SavedStateHandle,
) :
    ViewModel() {
    private val invalidId = NavigationRoutes.Arguments.INVALID_ID
    private val dummyCategory = Category(name = "Select category")
    private var isProductValid = false
    private var receivedCategoryName = ""
    private val productIdReceived =
        savedStateHandle.get<Int>(NavigationRoutes.Products.Arguments.ID)
            ?: invalidId
    private val _successObserver = MutableSharedFlow<Int>()
    val successObserver = _successObserver.asSharedFlow()
    val products = repository.getAllProducts()
    val allCategories = repository.getAllCategories()
    var productName by mutableStateOf("")
        private set
    var productCategory by mutableStateOf(dummyCategory)
        private set

    init {
        getProduct(productIdReceived)
    }

    fun confirmButtonClick() = viewModelScope.launch {
        val action = if (productIdReceived == invalidId) ::addProduct else ::updateProduct
        validateProduct(productName)
        if (isProductValid) {
            action()
        } else {
            _successObserver.emit(R.string.product_exists)
        }
    }

    fun updateName(input: String) {
        productName = input
    }

    fun chooseCategory(input: Category) {
        productCategory = input
    }

    private suspend fun addProduct() {
        val product = Product(name = productName, categoryId = productCategory.id)
        _successObserver.emit(R.string.product_added)
        repository.addProduct(product)
    }

    private suspend fun updateProduct() {
        val product = Product(productName, productCategory.id, id = productIdReceived)
        _successObserver.emit(R.string.product_updated)
        repository.updateProduct(product)
    }

    private suspend fun validateProduct(name: String) {
        val count = repository.doesProductExists(name)
        val duplicateValidation = count <= 0
        val currentNameValidation = name == receivedCategoryName
        isProductValid = duplicateValidation || currentNameValidation
    }

    private fun getProduct(id: Int) {
        if (id != invalidId) {
            viewModelScope.launch {
                val product = repository.getProduct(id)
                receivedCategoryName = product.name
                productName = product.name
                chooseCategory(repository.getCategory(product.categoryId))
            }
        }
    }
}