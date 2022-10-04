package com.symbol.shoppinglist.displayProducts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.symbol.shoppinglist.data.Product
import com.symbol.shoppinglist.database.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisplayProductViewModel @Inject constructor(
    private val repository: ProductsRepository
) :
    ViewModel() {
    val test = "test"


}