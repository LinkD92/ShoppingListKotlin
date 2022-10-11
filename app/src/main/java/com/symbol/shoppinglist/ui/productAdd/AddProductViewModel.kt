package com.symbol.shoppinglist.ui.productAdd

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.symbol.shoppinglist.R
import com.symbol.shoppinglist.database.ListRepository
import com.symbol.shoppinglist.database.entities.Category
import com.symbol.shoppinglist.database.entities.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    private val dummyCategory = Category("Select category")
    private val productReceived = savedStateHandle.get<String>("productName")

    val products = repository.getAllProducts()
    val allCategories = repository.getAllCategories()

    var productName by mutableStateOf(savedStateHandle.get<String>("productName") ?: "")
    var productCategory by mutableStateOf(allCategories.value?.get(0) ?: dummyCategory)

    private val _successObserver = MutableSharedFlow<String>()
    val successObserver = _successObserver.asSharedFlow()

    init {
        getProduct(productReceived)
    }

    fun addProduct() = viewModelScope.launch {
        val product = Product(productName, categoryName = productCategory.categoryName)
        try {
            repository.addProduct(product)
            _successObserver.emit(R.string.product_added.toString())
        } catch (error: SQLiteConstraintException) {
            _successObserver.emit(R.string.product_exists.toString())
        }
    }

    private fun getProduct(name: String?) {
        if (name != null) {
            viewModelScope.launch {
                val product = repository.getProduct(name)
                updateName(product.productName)
                chooseCategory(repository.getCategory(product.categoryName))
            }
        }
    }

    fun updateName(input: String) {
        productName = input
    }

    fun chooseCategory(input: Category) {
        productCategory = input
    }
}