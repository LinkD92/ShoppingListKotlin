package com.symbol.shoppinglist.ui.productAdd

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.symbol.shoppinglist.DispatcherProvider
import com.symbol.shoppinglist.FakeListRepository
import com.symbol.shoppinglist.TestDispatchers
import com.symbol.shoppinglist.database.local.entities.Category
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AddProductViewModelTest {

    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: AddProductViewModel
    private lateinit var category: Category
    private lateinit var dispatcher: TestDispatchers

    @Before
    fun setUp() {
        dispatcher = TestDispatchers()
        category = Category("TestCat", 100, false, 1)
        savedStateHandle = SavedStateHandle()
        viewModel = AddProductViewModel(FakeListRepository(), savedStateHandle, dispatcher)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `updateName should assign value to productName`() {
        //Given
        val name = "test"
        //When
        viewModel.updateName(name)
        //Then
        assertThat(viewModel.productName).isEqualTo(name)
    }

    @Test
    fun `chooseCategory should assign value to productCategory`() {
        // Given
        // When
        viewModel.chooseCategory(category)
        // Then
        assertThat(viewModel.productCategory).isEqualTo(category)
    }

    @Test
    fun `confirmButtonClick should post value on _successObserver`() = runTest(UnconfinedTestDispatcher()){
        viewModel.successObserver.test {
            viewModel.confirmButtonClick()
            assertThat(awaitItem()).isNotNull()
            cancelAndIgnoreRemainingEvents()
        }
    }
}










