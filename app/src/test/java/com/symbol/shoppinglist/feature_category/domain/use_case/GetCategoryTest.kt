package com.symbol.shoppinglist.feature_category.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.repository.CategoriesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before

import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class GetCategoryTest {

    @Mock
    private val repository = mock(CategoriesRepository::class.java)

    private lateinit var getCategory: GetCategory

    @Before
    fun setUp() {
        getCategory = GetCategory(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getCategory should call getCategory on repository`() = runTest {
        // Given
        val category = Category("categoryName", 1000, false, 5)
        val categoryId = 5

        // When
        `when`(repository.getCategory(categoryId)).thenReturn(category)
        val result = getCategory(categoryId)

        // Then
        assertThat(result).isEqualTo(category)
    }
}