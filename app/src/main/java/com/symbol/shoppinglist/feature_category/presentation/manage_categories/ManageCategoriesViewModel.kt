package com.symbol.shoppinglist.feature_category.presentation.manage_categories

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.symbol.shoppinglist.core.domain.util.OrderType
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.model.CategoryPromptMessage
import com.symbol.shoppinglist.feature_category.domain.use_case.CategoryUseCases
import com.symbol.shoppinglist.feature_category.domain.util.CategoryOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageCategoriesViewModel @Inject constructor(private val categoryUseCases: CategoryUseCases) :
    ViewModel() {

    private val _state = mutableStateOf(ManageCategoriesState())
    val state: State<ManageCategoriesState> = _state

    private val _eventFlow = MutableSharedFlow<CategoryPromptMessage>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var recentlyDeletedCategory: Category? = null

    private var getCategoriesJob: Job? = null

    init {
        getCategories(CategoryOrder.Name(OrderType.Ascending))
    }

    fun onEvent(event: ManageCategoriesEvent) {
        when (event) {
            is ManageCategoriesEvent.Order -> {}
            is ManageCategoriesEvent.DeleteCategory -> {
                viewModelScope.launch {
                    val prompt = categoryUseCases.deleteCategory(event.category)
                    recentlyDeletedCategory = event.category
                    _eventFlow.emit(prompt)
                }
            }
            is ManageCategoriesEvent.RestoreCategory -> {
                viewModelScope.launch {
                    val prompt =
                        categoryUseCases.insertCategory(recentlyDeletedCategory ?: return@launch)
                    recentlyDeletedCategory = null
                    _eventFlow.emit(prompt)
                }
            }
        }
    }

    private fun getCategories(categoryOrder: CategoryOrder) {
        getCategoriesJob?.cancel()
        getCategoriesJob = categoryUseCases.getCategories(categoryOrder)
            .onEach { categories ->
                _state.value =
                    state.value.copy(
                        categories = categories,
                        categoryOrder = categoryOrder
                    )
            }
            .launchIn(viewModelScope)
    }
}