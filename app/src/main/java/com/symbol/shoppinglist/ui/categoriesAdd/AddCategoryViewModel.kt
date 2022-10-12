package com.symbol.shoppinglist.ui.categoriesAdd

import android.database.sqlite.SQLiteConstraintException
import android.provider.ContactsContract
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.symbol.shoppinglist.database.ListRepository
import com.symbol.shoppinglist.database.entities.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class AddCategoryViewModel @Inject constructor(private val repository: ListRepository) :
    ViewModel() {
    var categoryName by mutableStateOf("")
    private var categoryColorHex by mutableStateOf("")
    var categoryColor by mutableStateOf(Color.Black)
    var test: Long by mutableStateOf(0)


    private val _successObserver = MutableSharedFlow<String>()
    val successObserver = _successObserver.asSharedFlow()
    val categories = repository.getAllCategories()


    fun addCategory() = viewModelScope.launch {
        try {
            val category = Category( categoryName = categoryName, categoryColor = test)
            repository.addCategory(category)
            _successObserver.emit("Category added")
        } catch (error: SQLiteConstraintException) {
            _successObserver.emit("Category already exists")
        }
    }

    fun updateCategoryName(input: String) {
        categoryName = input
    }

    fun updateCategoryColor(input: String) {
        categoryColorHex = input
        test = input.toLong(16)
    }

    fun updateCategoryColor(input: Color) {
        categoryColor = input
    }
}