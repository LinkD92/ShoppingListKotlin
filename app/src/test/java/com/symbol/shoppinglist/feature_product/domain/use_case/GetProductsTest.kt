package com.symbol.shoppinglist.feature_product.domain.use_case

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.symbol.shoppinglist.core.domain.util.OrderType
import com.symbol.shoppinglist.feature_product.data.repository.FakeProductRepository
import com.symbol.shoppinglist.feature_product.domain.model.Product
import com.symbol.shoppinglist.feature_product.domain.repository.ProductsRepository
import com.symbol.shoppinglist.feature_product.domain.model.ProductOrder
import com.symbol.shoppinglist.test_helper.RandomString
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`


class GetProductsTest {
    private lateinit var getProducts: GetProducts

    private lateinit var fakeProductRepository: FakeProductRepository

    private lateinit var randomString: RandomString

    @Mock
    private val productsRepository = Mockito.mock(ProductsRepository::class.java)

    @Before
    fun setUp() {
        fakeProductRepository = FakeProductRepository()
        getProducts = GetProducts(productsRepository)
        randomString = RandomString()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getProducts should return flow list of products sorted by name ascending`() = runTest {
        // Given
        val product1 = Product("aaa", 1, false, 10, 1)
        val product2 = Product("bbb", 1, false, 10, 2)
        val product3 = Product("ccc", 1, false, 10, 3)
        val productsList = listOf(product1, product2, product3)
        val flowList = flow { emit(productsList) }

        // When
        `when`(productsRepository.getAllProducts()).thenReturn(flowList)

        // Then
        getProducts(ProductOrder.Name(OrderType.Ascending)).test {
            val list = awaitItem()
            assertThat(list[0]).isEqualTo(product1)
            assertThat(list[1]).isEqualTo(product2)
            assertThat(list[2]).isEqualTo(product3)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getProducts should return flow list of products sorted by name descending`() = runTest {
        // Given
        val product1 = Product("aaa", 1, false, 10, 1)
        val product2 = Product("bbb", 1, false, 10, 2)
        val product3 = Product("ccc", 1, false, 10, 3)
        val productsList = listOf(product1, product2, product3)
        val flowList = flow { emit(productsList) }

        // When
        `when`(productsRepository.getAllProducts()).thenReturn(flowList)

        // Then
        getProducts(ProductOrder.Name(OrderType.Descending)).test {
            val list = awaitItem()
            assertThat(list[0]).isEqualTo(product3)
            assertThat(list[1]).isEqualTo(product2)
            assertThat(list[2]).isEqualTo(product1)
            cancelAndIgnoreRemainingEvents()
        }
    }
}