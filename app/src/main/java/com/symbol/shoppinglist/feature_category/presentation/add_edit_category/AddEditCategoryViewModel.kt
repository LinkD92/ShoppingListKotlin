package com.symbol.shoppinglist.feature_category.presentation.add_edit_category

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.symbol.shoppinglist.NavigationRoutes
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.use_case.CategoryUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditCategoryViewModel @Inject constructor(
    private val categoryUseCases: CategoryUseCases,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    private val invalidId = NavigationRoutes.Arguments.INVALID_ID
    private var currentCategoryId: Int = invalidId

    private val _categoryName = mutableStateOf("")
    val categoryName: State<String> = _categoryName

    private val _categoryColor = mutableStateOf<Long>(0)
    val categoryColor: State<Long> = _categoryColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<Int>(NavigationRoutes.Categories.Arguments.ID)?.let { categoryId ->
            if (categoryId != invalidId) {
                viewModelScope.launch {
                    categoryUseCases.getCategory(categoryId).also { category ->
                        currentCategoryId = category.id
                        _categoryName.value = category.name
                        _categoryColor.value = category.color
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveCategory : UiEvent()
    }

    fun onEvent(event: AddEditCategoryEvent) {
        when (event) {
            is AddEditCategoryEvent.EnteredName -> {
                _categoryName.value = event.value
            }
            is AddEditCategoryEvent.ChangeColor -> {
                _categoryColor.value = event.value
            }
            is AddEditCategoryEvent.SaveCategory -> {
                viewModelScope.launch {
                    if (currentCategoryId == invalidId) {
                        categoryUseCases.insertCategory(
                            Category(
                                name = categoryName.value,
                                color = categoryColor.value,
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveCategory)
                    } else {
                        categoryUseCases.insertCategory(
                            Category(
                                name = categoryName.value,
                                color = categoryColor.value,
                                id = currentCategoryId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveCategory)
                    }
                }
            }
        }
    }


//    private val invalidId = NavigationRoutes.Arguments.INVALID_ID
//    private val categoryIdReceived =
//        savedStateHandle.get<Int>(NavigationRoutes.Categories.Arguments.ID)
//            ?: invalidId
//    private var receivedCategoryName = ""
//    private val _successObserver = MutableSharedFlow<Int>()
//    val successObserver = _successObserver.asSharedFlow()
//    val categories = repository.getAllCategories()
//    var categoryName by mutableStateOf("")
//        private set
//    var categoryColorLong: Long by mutableStateOf(0)
//        private set
//
//    init {
//        getCategory(categoryIdReceived)
//    }
//
//    fun confirmButtonClick() = viewModelScope.launch {
//        val validatorMessage = validateFields()
//        val action = if (categoryIdReceived == invalidId) ::addCategory else ::updateCategory
//        if (validatorMessage == 0) {
//            action()
//        } else {
//            _successObserver.emit(validatorMessage)
//        }
//    }
//
//    fun updateName(input: String) {
//        categoryName = input
//    }
//
//    fun updateColor(input: String) {
//        categoryColorLong = input.toLong(16)
//    }
//
//    private fun getCategory(id: Int) {
//        if (id != invalidId) {
//            viewModelScope.launch {
//                val category = repository.getCategory(id)
//                receivedCategoryName = category.name
//                categoryName = category.name
//                categoryColorLong = category.color
//            }
//        }
//    }
//
//    private suspend fun addCategory() {
//        val category = Category(name = categoryName, color = categoryColorLong)
//        _successObserver.emit(R.string.category_added)
//        repository.addCategory(category)
//    }
//
//    private suspend fun updateCategory() {
//        val category = Category(categoryName, categoryColorLong, id = categoryIdReceived)
//        _successObserver.emit(R.string.category_updated)
//        repository.updateCategory(category)
//    }
//
//    private suspend fun checkExistingName(name: String): Boolean {
//        val count = repository.doesCategoryExists(name)
//        return count <= 0
//    }
//
//    private fun checkCurrentName(name: String): Boolean {
//        return name == receivedCategoryName
//    }
//
//    private suspend fun validateFields(): Int {
//        val defaultColor = 0
//        if (categoryName.length < FieldValidation.MIN_NAME_LENGTH
//            || categoryName.length > FieldValidation.MAX_NAME_LENGTH
//        ) {
//            return R.string.name_invalid
//        }
//        if (categoryColorLong == defaultColor.toLong()) {
//            return R.string.category_invalid_color
//        }
//        if (!(checkCurrentName(categoryName) || checkExistingName(categoryName))) {
//            return R.string.name_exsists
//        }
//        return 0
//    }
}