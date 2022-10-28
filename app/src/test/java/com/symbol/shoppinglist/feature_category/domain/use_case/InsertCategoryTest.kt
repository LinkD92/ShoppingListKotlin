package com.symbol.shoppinglist.feature_category.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.symbol.shoppinglist.FieldValidation
import com.symbol.shoppinglist.feature_category.data.repository.FakeCategoryRepository
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.model.CategoryPromptMessage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class InsertCategoryTest {

    private lateinit var insertCategory: InsertCategory
    private lateinit var fakeCategoryRepository: FakeCategoryRepository


    @Before
    fun setUp() {
        fakeCategoryRepository = FakeCategoryRepository()
        insertCategory = InsertCategory(fakeCategoryRepository)
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `insertCategory should return invalid length prompt on name less than 3 characters`() =
        runTest {
            // Given
            val category = Category("12", 1000, false, 1)

            // When
            val result = insertCategory(category)

            // Then
            assertThat(result).isEqualTo(CategoryPromptMessage.InvalidLength)
        }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `insertCategory should return invalid length prompt on name longer than 20 characters`() =
        runTest {
            // Given
            val category = Category("123456789012345678901", 1000, false, 1)

            // When
            val result = insertCategory(category)

            // Then
            assertThat(result).isEqualTo(CategoryPromptMessage.InvalidLength)
        }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `insertCategory should return success prompt on name between 3 and 20 characters`() =
        runTest {
            // Given
            val category1 = Category("123", 1000, false, 1)
            val category2 = Category("12345678901234567890", 1000, false, 1)

            // When
            val result1 = insertCategory(category1)
            val result2 = insertCategory(category2)

            // Then
            assertThat(result1).isEqualTo(CategoryPromptMessage.Success)
            assertThat(result2).isEqualTo(CategoryPromptMessage.Success)
        }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `insertCategory should return existing name prompt on inserting new category if name exists`() = runTest {
        // Given
        val existingName = "existingName"
        val nameValidator = ""
        val category = Category(existingName, 1000, false,1)
        insertCategory(category)

        // When
        val result = insertCategory(category, nameValidator)

        // Then
        assertThat(result).isEqualTo(CategoryPromptMessage.ExistingName)
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `insertCategory should return success name prompt on updating category`() = runTest {
        // Given
        val existingName = "existingName"
        val category = Category(existingName, 1000, false,1)
        val updatedCategory = Category(existingName, 3000, false,1)
        insertCategory(category)

        // When
        val result = insertCategory(updatedCategory, existingName)

        // Then
        assertThat(result).isEqualTo(CategoryPromptMessage.Success)
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `insertCategory should return success prompt on inserting valid category`() = runTest {
        // Given
        val category = Category("name", 1000, false,1)

        // When
        val result = insertCategory(category)

        // Then
        assertThat(result).isEqualTo(CategoryPromptMessage.Success)
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `insertCategory should return invalid color prompt on defaultColor`() = runTest {
        // Given
        val existingName = "existingName"
        val defaultColor = FieldValidation.DEFAULT_COLOR.toLong()
        val category = Category(existingName, defaultColor, false)

        // When
        val result1 = insertCategory(category)

        // Then
        assertThat(result1).isEqualTo(CategoryPromptMessage.InvalidColor)
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `insertCategory should return success prompt on non default color`() = runTest {
        // Given
        val existingName = "existingName"
        val nonDefaultColor = FieldValidation.DEFAULT_COLOR.toLong() +1
        val category = Category(existingName, nonDefaultColor, false)

        // When
        val result1 = insertCategory(category)

        // Then
        assertThat(result1).isEqualTo(CategoryPromptMessage.Success)
    }
}