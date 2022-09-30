package com.symbol.shoppinglist.displayProducts

import androidx.lifecycle.ViewModel
import com.symbol.shoppinglist.database.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DisplayProductViewModel @Inject constructor(
    private val repository: ProductsRepository
) :
    ViewModel() {
//    private val _isLoading: MutableLiveData<Boolean> by lazy {
//        MutableLiveData<Boolean>(false)
//    }
//    val products: LiveData<List<Product>> by lazy {
//        repository.getAllProducts()
//    }
//    val isLoading: LiveData<Boolean> get() = _isLoading
//
//    fun deleteProduct(product: Product) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.deleteProduct(product)
//        }
//    }
//    fun addProduct(product: Product){
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.addProduct(product)
//        }
//    }
}