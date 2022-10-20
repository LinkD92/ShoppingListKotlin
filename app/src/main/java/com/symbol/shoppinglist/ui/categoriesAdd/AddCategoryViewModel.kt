package com.symbol.shoppinglist.ui.categoriesAdd

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.symbol.shoppinglist.FieldValidation
import com.symbol.shoppinglist.NavigationRoutes
import com.symbol.shoppinglist.R
import com.symbol.shoppinglist.database.ListRepository
import com.symbol.shoppinglist.database.local.entities.Category
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
        val validatorMessage = validateFields()
        val action = if (categoryIdReceived == invalidId) ::addCategory else ::updateCategory
        if (validatorMessage == 0) {
            action()
        } else {
            _successObserver.emit(validatorMessage)
        }
    }

    fun updateName(input: String) {
        categoryName = input
    }

    fun updateColor(input: String) {
        categoryColorLong = input.toLong(16)
        Log.d("QWAS - updateColor:", "$categoryColorLong")
    }

    private fun getCategory(id: Int) {
        if (id != invalidId) {
            viewModelScope.launch {
                val category = repository.getCategory(id)
                receivedCategoryName = category.name
                categoryName = category.name
                categoryColorLong = category.color
            }
        }
    }

    private suspend fun addCategory() {
        val category = Category(name = categoryName, color = categoryColorLong)
        _successObserver.emit(R.string.category_added)
        repository.addCategory(category)
    }

    private suspend fun updateCategory() {
        val category = Category(categoryName, categoryColorLong, id = categoryIdReceived)
        _successObserver.emit(R.string.category_updated)
        repository.updateCategory(category)
    }

    private suspend fun checkExistingName(name: String): Boolean {
        val count = repository.doesCategoryExists(name)
        return count <= 0
    }

    private fun checkCurrentName(name: String): Boolean {
        return name == receivedCategoryName
    }

    private suspend fun validateFields(): Int {
        val defaultColor = 0
        if (categoryName.length < FieldValidation.MIN_NAME_LENGTH
            || categoryName.length > FieldValidation.MAX_NAME_LENGTH
        ) {
            return R.string.name_invalid
        }
        if (categoryColorLong == defaultColor.toLong()) {
            return R.string.category_invalid_color
        }
        if (!(checkCurrentName(categoryName) || checkExistingName(categoryName))) {
            return R.string.name_exsists
        }
        return 0
    }
}