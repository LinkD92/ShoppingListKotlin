package com.symbol.shoppinglist.feature_product.data.data_source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.symbol.shoppinglist.core.data.datasource.ListRoomDatabase
import com.symbol.shoppinglist.feature_product.domain.model.Product
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@SmallTest
class ProductsDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ListRoomDatabase
    private lateinit var dao: ProductsDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ListRoomDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.productsDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun getProducts() = runTest {
        // Given
        val product1 = Product("name1", 1, id = 1)
        val product2 = Product("name2", 2, id = 2)
        val product3 = Product("name3", 2, id = 3)
        val productsList = listOf(product1, product2, product3)

        // When
        dao.insertProduct(product1)
        dao.insertProduct(product2)
        dao.insertProduct(product3)

        // Then
        dao.getAllProducts().test {
            val list = awaitItem()
            assertThat(list.size).isEqualTo(3)
            assertThat(list).contains(product1)
            assertThat(list).contains(product2)
            assertThat(list).contains(product3)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun getProduct() = runTest {
        // Given
        val product1 = Product("name1", 1, id = 1)
        dao.insertProduct(product1)

        // When
        val product = dao.getProduct(1)

        // Then
        assertThat(product).isEqualTo(product1)
    }

    @Test
    fun getCategoryProducts() = runTest {
        // Given
        val product1 = Product("name1", 1, id = 1)
        val product2 = Product("name2", 2, id = 2)
        val product3 = Product("name3", 2, id = 3)
        dao.insertProduct(product1)
        dao.insertProduct(product2)
        dao.insertProduct(product3)

        // When

        // Then
        dao.getCategoryProducts(2).test {
            val list = awaitItem()
            assertThat(list.size).isEqualTo(2)
            assertThat(list).contains(product2)
            assertThat(list).contains(product3)
            assertThat(list).doesNotContain(product1)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun productNameCount() = runTest {
        // Given
        val name = "name"
        val product1 = Product(name, 1)
        dao.insertProduct(product1)
        // When

        val result = dao.productNameCount(name)

        // Then
        assertThat(result).isEqualTo(1)
    }

    @Test
    fun insertProduct() = runTest {
        // Given
        val product1 = Product("name1", 1, id = 1)

        // When
        dao.insertProduct(product1)

        // Then
        dao.getAllProducts().test {
            val list = awaitItem()
            assertThat(list.size).isEqualTo(1)
            assertThat(list).contains(product1)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun insertProducts() = runTest {
        // Given
        val product1 = Product("name1", 1, id = 1)
        val product2 = Product("name2", 1, id = 2)
        val productsList = listOf(product1, product2)

        // When
        dao.insertProducts(productsList)

        // Then
        dao.getAllProducts().test {
            val list = awaitItem()
            assertThat(list.size).isEqualTo(2)
            assertThat(list).contains(product1)
            assertThat(list).contains(product2)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun deleteProduct() = runTest {
        // Given
        val product1 = Product("name1", 1, id = 1)
        val product2 = Product("name1", 1, id = 2)
        dao.insertProduct(product1)
        dao.insertProduct(product2)

        // When
        dao.deleteProduct(product1)

        // Then
        dao.getAllProducts().test {
            val list = awaitItem()
            assertThat(list).doesNotContain(product1)
            assertThat(list.size).isEqualTo(1)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun deleteCategoryProduct() = runTest {
        // Given
        val categoryId = 2
        val product1 = Product("name1", 1, id = 1)
        val product2 = Product("name2", categoryId, id = 2)
        val product3 = Product("name3", categoryId, id = 3)
        dao.insertProduct(product1)
        dao.insertProduct(product2)
        dao.insertProduct(product3)

        // When

        // Then
        dao.getCategoryProducts(categoryId).test {
            val list = awaitItem()
            assertThat(list.size).isEqualTo(2)
            assertThat(list).contains(product2)
            assertThat(list).contains(product3)
            assertThat(list).doesNotContain(product1)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun deleteProductById() = runTest {
        // Given
        val productId = 1
        val product1 = Product("name1", 1, id = productId)
        dao.insertProduct(product1)

        // When
        dao.deleteProductById(productId)

        // Then
        dao.getAllProducts().test {
            val list = awaitItem()
            assertThat(list).doesNotContain(product1)
            cancelAndIgnoreRemainingEvents()
        }
    }
}