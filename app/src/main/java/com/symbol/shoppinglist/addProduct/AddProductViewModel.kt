package com.symbol.shoppinglist.addProduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.symbol.shoppinglist.data.Product
import com.symbol.shoppinglist.database.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(private val repository: ProductsRepository) :
    ViewModel() {

    fun insertProduct(product: Product) = viewModelScope.launch {
        repository.addProduct(product)
    }
}