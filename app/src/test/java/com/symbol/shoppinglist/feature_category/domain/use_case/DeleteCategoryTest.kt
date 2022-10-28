package com.symbol.shoppinglist.feature_category.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.symbol.shoppinglist.feature_category.data.repository.FakeCategoryRepository
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.model.CategoryPromptMessage
import com.symbol.shoppinglist.feature_product.data.repository.FakeProductRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DeleteCategoryTest {

    private lateinit var deleteCategory: DeleteCategory
    private lateinit var fakeCategoryRepository: FakeCategoryRepository
    private lateinit var fakeProductRepository: FakeProductRepository


    @Before
    fun setUp() {
        fakeCategoryRepository = FakeCategoryRepository()
        fakeProductRepository = FakeProductRepository()
        deleteCategory = DeleteCategory(fakeCategoryRepository, fakeProductRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `delete category should return success when called`() = runTest {
        // Given
        val category = Category("name", 1000, false, 1)

        // When
        val result = deleteCategory(category)

        // Then
        assertThat(result).isEqualTo(CategoryPromptMessage.CategoryDeleted)
    }
}