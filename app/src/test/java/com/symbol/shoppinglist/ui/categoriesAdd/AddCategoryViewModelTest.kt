package com.symbol.shoppinglist.ui.categoriesAdd

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import com.symbol.shoppinglist.FakeListRepository
import com.symbol.shoppinglist.database.local.entities.Category
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

class AddCategoryViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: AddCategoryViewModel

    lateinit var savedStateHandle: SavedStateHandle

    lateinit var category: Category

    @Before
    fun setup() {
        category = Category("Name", 100, false, 1)
        savedStateHandle = SavedStateHandle()
        viewModel = AddCategoryViewModel(FakeListRepository(), savedStateHandle)
    }

    @Test
    fun `updateName should assign value to categoryName`() {
        //Given
        val testName = "test"

        //When
        viewModel.updateName(testName)

        //Then
        assertThat(viewModel.categoryName).isEqualTo(testName)
    }

    @Test
    fun `updateColor should assign value to categoryColorLong as Long in Hex`() {
        //Given
        val color = "1000"
        val colorInLongHex = color.toLong(16)

        //When
        viewModel.updateColor(color)

        //Then
        assertThat(viewModel.categoryColorLong).isEqualTo(colorInLongHex)
    }

    @Test
    fun `checkExistingName should set isCategoryValid to true when name is unique`() {

    }


}