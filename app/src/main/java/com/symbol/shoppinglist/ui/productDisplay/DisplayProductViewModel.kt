package com.symbol.shoppinglist.ui.productDisplay

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.symbol.shoppinglist.database.ListRepository
import com.symbol.shoppinglist.database.entities.Category
import com.symbol.shoppinglist.database.entities.Product
import com.symbol.shoppinglist.database.entities.relations.CategoryWithProducts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisplayProductViewModel @Inject constructor(private val repository: ListRepository) :
    ViewModel() {
    val allProducts: LiveData<List<Product>> = repository.getAllProducts()

    private val _list = mutableStateOf<List<CategoryWithProducts>>(listOf())
    val list: State<List<CategoryWithProducts>> = _list

    fun getAllProducts() {
        viewModelScope.launch {
            _list.value = repository.getCategoriesWithProducts().toMutableStateList()
        }
    }

    fun updateProduct(product: Product){
        viewModelScope.launch {
            product.isProductChecked = !product.isProductChecked
            repository.updateProduct(product)
        }
    }

    fun deleteProduct(product: Product){
        viewModelScope.launch {
            repository.deleteProduct(product)
        }
    }
}