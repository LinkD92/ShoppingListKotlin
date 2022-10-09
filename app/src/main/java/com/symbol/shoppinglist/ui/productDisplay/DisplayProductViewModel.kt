package com.symbol.shoppinglist.ui.productDisplay

import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.symbol.shoppinglist.database.ListRepository
import com.symbol.shoppinglist.database.entities.Product
import com.symbol.shoppinglist.database.entities.relations.CategoryWithProducts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisplayProductViewModel @Inject constructor(private val repository: ListRepository) :
    ViewModel() {
    val allProducts: LiveData<List<Product>> = repository.getAllProducts()
    var expand by mutableStateOf(false)
    var productState by mutableStateOf<Product?>(null)

//    val testList = mutableStateListOf<CategoryWithProducts>().apply { addAll(getAllProducts()) }

    private val _list = mutableStateOf<List<CategoryWithProducts>>(listOf())
    val list: State<List<CategoryWithProducts>> = _list

    init {
        getAllProducts()
    }

    fun getAllProducts() {
        viewModelScope.launch {
            _list.value = repository.getCategoriesWithProducts()
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            product.isProductChecked = !product.isProductChecked
            repository.updateProduct(product)
            productState = product
//            getAllProducts()
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            repository.deleteProduct(product)
//            getAllProducts()
        }
    }

    fun changeExpand() {
        expand = !expand
    }
}