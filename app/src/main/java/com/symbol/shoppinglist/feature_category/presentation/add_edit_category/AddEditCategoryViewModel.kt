package com.symbol.shoppinglist.feature_category.presentation.add_edit_category

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.symbol.shoppinglist.core.data.util.NavigationRoutes
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.model.CategoryPromptMessage
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
    private var categoryNameValidator: String =""

    private val _categoryName = mutableStateOf("")
    val categoryName: State<String> = _categoryName

    private val _categoryColor = mutableStateOf<Long>(0)
    val categoryColor: State<Long> = _categoryColor

    private val _eventFlow = MutableSharedFlow<CategoryPromptMessage>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<Int>(NavigationRoutes.Categories.Arguments.ID)?.let { categoryId ->
            if (categoryId != invalidId) {
                viewModelScope.launch {
                    categoryUseCases.getCategory(categoryId).also { category ->
                        currentCategoryId = category.id
                        _categoryName.value = category.name
                        _categoryColor.value = category.color
                        categoryNameValidator = category.name
                    }
                }
            }
        }
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
                    val prompt = categoryUseCases.insertCategory(
                        if (currentCategoryId == invalidId) {
                            Category(
                                name = categoryName.value,
                                color = categoryColor.value,
                            )
                        } else {
                            Category(
                                name = categoryName.value,
                                color = categoryColor.value,
                                id = currentCategoryId
                            )
                        },
                     categoryNameValidator
                    )
                    _eventFlow.emit(prompt)
                }
            }
        }
    }
}