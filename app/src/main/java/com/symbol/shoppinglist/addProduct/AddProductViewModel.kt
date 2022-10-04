package com.symbol.shoppinglist.addProduct

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.symbol.shoppinglist.data.Product
import com.symbol.shoppinglist.database.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(private val repository: ProductsRepository): ViewModel() {
    val productName = MutableLiveData<String>()

    fun addProduct(product: Product) = viewModelScope.launch { repository.addProduct(product) }
}