package com.symbol.shoppinglist.feature_product.domain.use_case

import com.google.common.truth.Truth
import com.symbol.shoppinglist.FieldValidation
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.model.CategoryPromptMessage
import com.symbol.shoppinglist.feature_product.data.repository.FakeProductRepository
import com.symbol.shoppinglist.feature_product.domain.model.Product
import com.symbol.shoppinglist.feature_product.domain.model.ProductPromptMessage
import com.symbol.shoppinglist.feature_product.domain.repository.ProductsRepository
import com.symbol.shoppinglist.test_helper.RandomString
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock

class InsertProductTest {

    private lateinit var insertProduct: InsertProduct

    private lateinit var fakeProductRepository: FakeProductRepository

    private lateinit var randomString: RandomString

    @Mock
    private val productsRepository = mock(ProductsRepository::class.java)

    @Before
    fun setUp() {
        fakeProductRepository = FakeProductRepository()
        insertProduct = InsertProduct(fakeProductRepository)
        randomString = RandomString()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `insertProduct should return invalid length prompt on name less than MIN_NAME_LENGTH characters`() =
        runTest {
            // Given
            val productName = randomString(FieldValidation.MIN_NAME_LENGTH - 1)
            val product = Product(productName, 1, false, 10, 1)

            // When
            val result = insertProduct(product)

            // Then
            Truth.assertThat(result).isEqualTo(ProductPromptMessage.InvalidLength)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `insertProduct should return invalid length prompt on name less than MAX_NAME_LENGTH characters`() =
        runTest {
            // Given
            val productName = randomString(FieldValidation.MAX_NAME_LENGTH + 1)
            val product = Product(productName, 1, false, 10, 1)

            // When
            val result = insertProduct(product)

            // Then
            Truth.assertThat(result).isEqualTo(ProductPromptMessage.InvalidLength)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `insertProduct should return success prompt on name between MIN and MAX_NAME_LENGTH characters`() =
        runTest {
            // Given
            val productName = randomString(FieldValidation.MAX_NAME_LENGTH - 1)
            val product = Product(productName, 1, false, 10, 1)

            // When
            val result = insertProduct(product)

            // Then
            Truth.assertThat(result).isEqualTo(ProductPromptMessage.Success)
        }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `insertProduct should return existing name prompt on new category insert if name exists`() =
        runTest {
            // Given
            val nameValidator = ""
            val productName = randomString(FieldValidation.MAX_NAME_LENGTH - 1)
            val product = Product(productName, 1, false, 10, 1)
            insertProduct(product)

            // When
            val result = insertProduct(product, nameValidator)

            // Then
            Truth.assertThat(result).isEqualTo(ProductPromptMessage.ExistingName)
        }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `insertProduct should return success prompt on product update`() =
        runTest {
            // Given
            val productName = randomString(FieldValidation.MAX_NAME_LENGTH - 1)
            val product = Product(productName, 1, false, 10, 1)
            insertProduct(product)

            // When
            val result = insertProduct(product, productName)

            // Then
            Truth.assertThat(result).isEqualTo(ProductPromptMessage.Success)
        }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `insertCategory should return invalid category prompt on defaultCategory`() = runTest {
        // Given
        val productName = randomString(FieldValidation.MAX_NAME_LENGTH - 1)
        val product = Product(productName, -1, false, 10, 1)
        insertProduct(product)

        // When
        val result = insertProduct(product, productName)

        // Then
        Truth.assertThat(result).isEqualTo(ProductPromptMessage.InvalidCategory)
    }
}