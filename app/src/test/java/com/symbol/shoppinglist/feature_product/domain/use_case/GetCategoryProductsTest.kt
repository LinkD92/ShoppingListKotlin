package com.symbol.shoppinglist.feature_product.domain.use_case

import com.symbol.shoppinglist.feature_product.data.repository.FakeProductRepository
import com.symbol.shoppinglist.feature_product.domain.repository.ProductsRepository
import com.symbol.shoppinglist.test_helper.RandomString
import kotlinx.coroutines.test.runTest
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class GetCategoryProductsTest {

    private lateinit var getCategoryProducts: GetCategoryProducts

    private lateinit var fakeProductRepository: FakeProductRepository

    private lateinit var randomString: RandomString

    @Mock
    private val productsRepository = Mockito.mock(ProductsRepository::class.java)

    @Before
    fun setUp() {
        fakeProductRepository = FakeProductRepository()
        getCategoryProducts = GetCategoryProducts(fakeProductRepository)
        randomString = RandomString()
    }

    @Test
    fun `getCategoryProducts should return products of category`() = runTest {
        // Given


        // When

        // Then
    }
}