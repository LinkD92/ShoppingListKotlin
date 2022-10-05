package com.symbol.shoppinglist.ui.productAdd

import android.database.sqlite.SQLiteConstraintException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.symbol.shoppinglist.database.ListRepository
import com.symbol.shoppinglist.database.entities.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(private val repository: ListRepository) :
    ViewModel() {
    var productName by mutableStateOf("")

    private val _successObserver = MutableSharedFlow<String>()
    val successObserver = _successObserver.asSharedFlow()

    val products = repository.getAllProducts()

    fun addProduct() = viewModelScope.launch {
        val categoryName = "cat1"
        val product = Product(productName, categoryName = categoryName)
//            .also {
//                it.category = _product.value.category
//                it.price = _product.value.price
//            }
        try {
            repository.addProduct(product)
            _successObserver.emit("Product added")
        } catch (error: SQLiteConstraintException) {
            _successObserver.emit("Product already exists")
        }
    }

    fun updateName(input: String) {
        productName = input
    }
}