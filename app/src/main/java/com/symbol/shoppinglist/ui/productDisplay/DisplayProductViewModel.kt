package com.symbol.shoppinglist.ui.productDisplay

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.symbol.shoppinglist.database.ListRepository
import com.symbol.shoppinglist.database.entities.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DisplayProductViewModel @Inject constructor(private val repository: ListRepository) :
    ViewModel() {
    val allProducts: LiveData<List<Product>> = repository.getAllProducts()
//
//    init {
//        initData()
//    }
//
//    val list =
//        mutableListOf(
//            Product("test11"),
//            Product("test22"),
//            Product("test33"),
//        )
//
//    private fun initData() {
//        viewModelScope.launch {
//            list.forEach { product -> repository.addProduct(product) }
//        }
//    }


}