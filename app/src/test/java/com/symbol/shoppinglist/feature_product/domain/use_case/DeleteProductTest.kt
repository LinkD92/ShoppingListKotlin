package com.symbol.shoppinglist.feature_product.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.symbol.shoppinglist.core.data.util.FieldValidation
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
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class DeleteProductTest {
    private lateinit var deleteProduct: DeleteProduct

    private lateinit var fakeProductRepository: FakeProductRepository

    private lateinit var randomString: RandomString

    @Mock
    private val productsRepository = Mockito.mock(ProductsRepository::class.java)

    @Before
    fun setUp() {
        fakeProductRepository = FakeProductRepository()
        deleteProduct = DeleteProduct(productsRepository)
        randomString = RandomString()
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `deleteProduct should return product deleted prompt`()= runTest {
        // Given
        val productName = randomString(FieldValidation.MIN_NAME_LENGTH - 1)
        val product = Product(productName, 1, false, 10, 1)

        // When
        `when`(productsRepository.deleteProduct(product)).thenReturn(Unit)
        val result = deleteProduct(product.id)

        // Then
        assertThat(result).isEqualTo(ProductPromptMessage.ProductDeleted)
    }
}