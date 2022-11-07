package com.symbol.shoppinglist.feature_category.domain.use_case

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.symbol.shoppinglist.DispatcherProvider
import com.symbol.shoppinglist.core.domain.util.TestDispatchers
import com.symbol.shoppinglist.feature_category.data.repository.FakeCategoryRepository
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.repository.CategoriesRepository
import com.symbol.shoppinglist.feature_category.domain.util.CategoryOrderType
import com.symbol.shoppinglist.feature_category.domain.util.FullCategoryOrderType
import com.symbol.shoppinglist.feature_category.domain.util.SortType
import com.symbol.shoppinglist.feature_settings.domain.repository.PreferencesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

    private lateinit var category1: Category
    private lateinit var category2: Category
    private lateinit var category3: Category
    private lateinit var categoryList: List<Category>
    private lateinit var flowList: Flow<List<Category>>

    @Mock private val repository = mock(CategoriesRepository::class.java)

    @Mock private val preferencesRepository = mock(PreferencesRepository::class.java)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        category1 = Category("aaa", customOrder = 2)
        category2 = Category("bbb", customOrder = 0)
        category3 = Category("ccc", customOrder = 1)
        categoryList = listOf(category1, category2, category3)
        flowList = flow {
            emit(categoryList)
        }
        `when`(repository.getAllCategories()).thenReturn(flowList)
        dispatcherProvider = TestDispatchers()
        fakeCategoryRepository = FakeCategoryRepository()

        getCategories = GetCategories(repository, preferencesRepository)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getCategories should return Flow list of categories ascending`() = runTest {
        // Given
        val fullCategoryOrderType =
            FullCategoryOrderType(CategoryOrderType.NAME, SortType.ASCENDING)

        // When

        // Then
        getCategories(fullCategoryOrderType).test {
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
        val fullCategoryOrderType =
            FullCategoryOrderType(CategoryOrderType.NAME, SortType.DESCENDING)

        // When

        // Then
        getCategories(fullCategoryOrderType).test {
            val list = awaitItem()
            assertThat(list[0]).isEqualTo(category3)
            assertThat(list[1]).isEqualTo(category2)
            assertThat(list[2]).isEqualTo(category1)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getCategories should return Flow list of categories customOrder`() = runTest {
        // Given
        val fullCategoryOrderType = FullCategoryOrderType(sortType = SortType.CUSTOM)

        // When

        // Then
        getCategories(fullCategoryOrderType).test {
            val list = awaitItem()
            assertThat(list[0]).isEqualTo(category2)
            assertThat(list[1]).isEqualTo(category3)
            assertThat(list[2]).isEqualTo(category1)
            cancelAndIgnoreRemainingEvents()
        }
    }
}