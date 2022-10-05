package com.symbol.shoppinglist.ui.categoriesAdd

import android.database.sqlite.SQLiteConstraintException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.symbol.shoppinglist.database.ListRepository
import com.symbol.shoppinglist.database.entities.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCategoryViewModel @Inject constructor(private val repository: ListRepository) :
    ViewModel() {
    var categoryName by mutableStateOf("")

    private val _successObserver = MutableSharedFlow<String>()
    val successObserver = _successObserver.asSharedFlow()
    val categories = repository.getAllCategories()

    fun addCategory() = viewModelScope.launch {
        val category = Category(categoryName)
        try {
            repository.addCategory(category)
            _successObserver.emit("Category added")
        } catch (error: SQLiteConstraintException) {
            _successObserver.emit("Category already exists")
        }
    }

    fun updateCategoryName(input: String) {
        categoryName = input
    }
}