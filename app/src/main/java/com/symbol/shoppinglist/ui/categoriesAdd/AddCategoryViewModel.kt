package com.symbol.shoppinglist.ui.categoriesAdd

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.symbol.shoppinglist.NavigationRoutes
import com.symbol.shoppinglist.R
import com.symbol.shoppinglist.database.ListRepository
import com.symbol.shoppinglist.database.entities.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCategoryViewModel @Inject constructor(
    private val repository: ListRepository,
    private val savedStateHandle: SavedStateHandle,
) :
    ViewModel() {
    private val invalidId = NavigationRoutes.Arguments.INVALID_ID
    private val categoryIdReceived =
        savedStateHandle.get<Int>(NavigationRoutes.Categories.Arguments.ID)
            ?: invalidId
    private var receivedCategoryName = ""
    private val _successObserver = MutableSharedFlow<Int>()
    private var isCategoryValid = false
    val successObserver = _successObserver.asSharedFlow()
    val categories = repository.getAllCategories()
    var categoryName by mutableStateOf("")
        private set
    var categoryColorLong: Long by mutableStateOf(0)
        private set

    init {
        getCategory(categoryIdReceived)
    }

    fun confirmButtonClick() = viewModelScope.launch {
        val action = if (categoryIdReceived == invalidId) ::addCategory else ::updateCategory
        validateCategory(categoryName)
        if (isCategoryValid) {
            action()
        } else {
            _successObserver.emit(R.string.category_exists)
        }
    }

    fun updateName(input: String) {
        categoryName = input
    }

    fun updateColor(input: String) {
        categoryColorLong = input.toLong(16)
    }

    private fun getCategory(id: Int) {
        if (id != invalidId) {
            viewModelScope.launch {
                val category = repository.getCategory(id)
                receivedCategoryName = category.categoryName
                categoryName = category.categoryName
                categoryColorLong = category.categoryColor
            }
        }
    }

    private suspend fun addCategory() {
        val category = Category(categoryName = categoryName, categoryColor = categoryColorLong)
        _successObserver.emit(R.string.category_added)
        repository.addCategory(category)
    }

    private suspend fun updateCategory() {
        val category = Category(categoryIdReceived, categoryName, categoryColorLong)
        _successObserver.emit(R.string.category_updated)
        repository.updateCategory(category)
    }

    private suspend fun validateCategory(name: String) {
        val count = repository.doesCategoryExists(name)
        val duplicateValidation = count <= 0
        val currentNameValidation = name == receivedCategoryName
        isCategoryValid = duplicateValidation || currentNameValidation
    }
}