package com.symbol.shoppinglist.ui.productDisplay

import androidx.compose.runtime.Immutable
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.symbol.shoppinglist.database.ListRepository
import com.symbol.shoppinglist.database.local.entities.Category
import com.symbol.shoppinglist.database.local.entities.Product
import com.symbol.shoppinglist.database.local.entities.relations.CategoryWithProducts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisplayProductViewModel @Inject constructor(
    private val repository: ListRepository
) :
    ViewModel() {


    private val _categoriesWithProducts = repository.getCategoriesWithProducts()
    val categoriesWithProducts: LiveData<List<CategoryWithProducts>> = _categoriesWithProducts

    val categories = repository.getCategories()

    val state = repository.getCategoriesWithProductsFlow()

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            delay(50)

            repository.updateProduct(product)
        }
    }

    fun getCategoriesProduct(categoryId: Int): Flow<List<Product>> =
        repository.getCategoryProducts(categoryId)

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            repository.deleteProduct(product)
        }
    }

    fun deleteProductById(productId: Int) {
        viewModelScope.launch {
            repository.deleteProductById(productId)
        }
    }

    fun updateCategory(category: Category) {
        viewModelScope.launch {
            repository.updateCategory(category)
        }
    }

    fun changeCategoryExpand(category: Category, isExpanded: Boolean) {
        viewModelScope.launch {
            repository.updateCategory(category.apply { this.isExpanded = isExpanded })
        }
    }
}

@Immutable
internal data class DisplayProductsState(
    val watches: List<CategoryWithProducts> = emptyList(),
) {
    companion object {
        val Empty = DisplayProductsState()
    }
}
