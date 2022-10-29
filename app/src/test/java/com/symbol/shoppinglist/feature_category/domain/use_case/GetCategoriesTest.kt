package com.symbol.shoppinglist.feature_category.domain.use_case

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.symbol.shoppinglist.DispatcherProvider
import com.symbol.shoppinglist.core.domain.util.OrderType
import com.symbol.shoppinglist.core.domain.util.TestDispatchers
import com.symbol.shoppinglist.feature_category.data.repository.FakeCategoryRepository
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.repository.CategoriesRepository
import com.symbol.shoppinglist.feature_category.domain.util.CategoryOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class GetCategoriesTest {

    private lateinit var getCategories: GetCategories
    private lateinit var fakeCategoryRepository: FakeCategoryRepository
    private lateinit var dispatcherProvider: DispatcherProvider

    @Mock
    private val repository = mock(CategoriesRepository::class.java)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        dispatcherProvider = TestDispatchers()
        fakeCategoryRepository = FakeCategoryRepository()

        getCategories = GetCategories(repository, dispatcherProvider)


    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getCategories should return Flow list of categories ascending`() = runTest {
        // Given
        val category1 = Category("aaa")
        val category2 = Category("bbb")
        val category3 = Category("ccc")
        val categoryList = listOf(category1, category2, category3)
        val flowList = flow {
            emit(categoryList)
        }

        // When
        `when`(repository.getAllCategories()).thenReturn(flowList)

        // Then
        getCategories(CategoryOrder.Name(OrderType.Ascending)).test {
            val list = awaitItem()
            assertThat(list[0]).isEqualTo(category1)
            assertThat(list[1]).isEqualTo(category2)
            assertThat(list[2]).isEqualTo(category3)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getCategories should return Flow list of categories descending`() = runTest {
        // Given
        val category1 = Category("aaa")
        val category2 = Category("bbb")
        val category3 = Category("ccc")
        val categoryList = listOf(category1, category2, category3)
        val flowList = flow {
            emit(categoryList)
        }

        // When
        `when`(repository.getAllCategories()).thenReturn(flowList)

        // Then
        getCategories(CategoryOrder.Name(OrderType.Descending)).test{
            val list = awaitItem()
            assertThat(list[0]).isEqualTo(category3)
            assertThat(list[1]).isEqualTo(category2)
            assertThat(list[2]).isEqualTo(category1)
            cancelAndIgnoreRemainingEvents()
        }
    }
}