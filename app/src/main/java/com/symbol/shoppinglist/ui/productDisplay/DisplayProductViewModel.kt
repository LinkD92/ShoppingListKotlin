package com.symbol.shoppinglist.ui.productDisplay

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.symbol.shoppinglist.database.DefaultListRepository
import com.symbol.shoppinglist.database.ListRepository
import com.symbol.shoppinglist.database.local.entities.Category
import com.symbol.shoppinglist.database.local.entities.Product
import com.symbol.shoppinglist.database.local.entities.relations.CategoryWithProducts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisplayProductViewModel @Inject constructor(
    private val repository: ListRepository
) :
    ViewModel() {

    private val _categoriesWithProducts = repository.getCategoriesWithProducts()
    val categoriesWithProducts: LiveData<List<CategoryWithProducts>> = _categoriesWithProducts

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            repository.updateProduct(product)
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            repository.deleteProduct(product)
        }
    }

    fun updateCategory(category: Category) {
        viewModelScope.launch {
            repository.updateCategory(category)
        }
    }

    fun changeCategoryExpand(category: Category, isExpanded: Boolean){
        viewModelScope.launch {
            repository.updateCategory(category.apply { this.isExpanded = isExpanded })
        }
    }
}