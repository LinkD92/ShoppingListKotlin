package com.symbol.shoppinglist.database.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.symbol.shoppinglist.database.local.entities.Category
import com.symbol.shoppinglist.database.local.entities.Product
import getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ListDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ListDatabase
    private lateinit var dao: ListDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ListDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.listDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun addProduct() = runTest {
        //Given
        val product = Product("prod1", 1, false, id = 1)

        //When
        dao.addProduct(product)
        val allProducts = dao.getAllProducts().getOrAwaitValue()

        //Then
        assertThat(allProducts).contains(product)
    }

    @Test
    fun deleteProduct() = runTest {
        //Given
        val product = Product("prod1", 1, false)
        dao.addProduct(product)

        //When
        dao.deleteProduct(product)
        val allProducts = dao.getAllCategories().getOrAwaitValue()

        //Then
        assertThat(allProducts).doesNotContain(product)
    }

    @Test
    fun observerAllProducts() = runTest {
        //Given
        val product1 = Product("prod1", 1, false, id = 1)
        val product2 = Product("prod2", 2, true, id = 2)
        val product3 = Product("prod3", 1, true, id = 3)

        //When
        dao.addProduct(product1)
        dao.addProduct(product2)
        dao.addProduct(product3)
        val allProducts = dao.getAllProducts().getOrAwaitValue()

        //Then
        assertThat(allProducts.size).isEqualTo(3)
    }

    @Test
    fun updateProduct() = runTest {
        //Given
        val product = Product("prod1", 1, false, id = 1)
        val updatedProduct = product.apply {
            name = "prod2"
            isChecked = true
        }
        dao.addProduct(product)

        //When
        dao.updateProduct(updatedProduct)
        val allProducts = dao.getAllProducts().getOrAwaitValue()

        //Then
        assertThat(allProducts).contains(updatedProduct)
    }

    @Test
    fun doesProductExists_returns1() = runTest {
        //Given
        val existingProduct = Product("prod1", 1, false)
        val productNameToCheck = "prod1"
        dao.addProduct(existingProduct)

        //When
        val doesProductExists = dao.doesProductExists(productNameToCheck)

        //Then
        assertThat(doesProductExists).isEqualTo(1)
    }

    @Test
    fun doesProductExists_returns0() = runTest {
        //Given
        val existingProduct = Product("prod1", 100, false, 1)
        val productNameToCheck = "xxx"
        dao.addProduct(existingProduct)

        //When
        val doesProductExists = dao.doesProductExists(productNameToCheck)

        //Then
        assertThat(doesProductExists).isEqualTo(0)
    }

    @Test
    fun addCategory() = runTest {
        //Given
        val category = Category("cat1", 100, false, 1)

        //When
        dao.addCategory(category)
        val allCategories = dao.getAllCategories().getOrAwaitValue()

        //Then
        assertThat(allCategories).contains(category)
    }

    @Test
    fun deleteCategory() = runTest {
        //Given
        val category = Category("cat1", 100, false, 1)
        dao.addCategory(category)

        //When
        dao.deleteCategory(category)
        val allCategories = dao.getAllCategories().getOrAwaitValue()

        //Then
        assertThat(allCategories).doesNotContain(category)
    }

    @Test
    fun observerAllCategories() = runTest {
        //Given
        val category1 = Category("cat1", 100, false, 1)
        val category2 = Category("cat2", 1000, false, 2)
        val category3 = Category("cat3", 1000, false, 3)

        //When
        dao.addCategory(category1)
        dao.addCategory(category2)
        dao.addCategory(category3)
        val allCategories = dao.getAllCategories().getOrAwaitValue()

        //Then
        assertThat(allCategories.size).isEqualTo(3)
    }

    @Test
    fun updateCategory() = runTest {
        //Given
        val category = Category("cat1", 100, false, 1)
        val updatedCategory = category.apply {
            name = "cat2"
            color = 1000
        }
        dao.addCategory(category)

        //When
        dao.updateCategory(updatedCategory)
        val allCategories = dao.getAllCategories().getOrAwaitValue()

        //Then
        assertThat(allCategories).contains(updatedCategory)
    }

    @Test
    fun doesCategoryExists_returns1() = runTest {
        //Given
        val existingCategory = Category("cat1", 100, false, 1)
        val categoryNameToCheck = "cat1"
        dao.addCategory(existingCategory)

        //When
        val doesCategoryExists = dao.doesCategoryExists(categoryNameToCheck)

        //Then
        assertThat(doesCategoryExists).isEqualTo(1)
    }

    @Test
    fun doesCategoryExists_returns0() = runTest {
        //Given
        val existingCategory = Category("cat1", 100, false, 1)
        val categoryNameToCheck = "xxx"
        dao.addCategory(existingCategory)

        //When
        val doesCategoryExists = dao.doesCategoryExists(categoryNameToCheck)

        //Then
        assertThat(doesCategoryExists).isEqualTo(0)
    }

    @Test
    fun getCategoryWithProducts() = runTest {
        //Given
        val category1 = Category("cat1", 100, false, 1)
        val category2 = Category("cat2", 100, false, 2)
        val product = Product("prod1", 1, false, id = 1)
        dao.addCategory(category1)
        dao.addCategory(category2)
        dao.addProduct(product)

        //When
        val categoriesWithProducts = dao.getCategoryWithProducts().getOrAwaitValue()

        //Then
        assertThat(categoriesWithProducts.size).isEqualTo(2)
        assertThat(categoriesWithProducts[0].products).contains(product)
        assertThat(categoriesWithProducts[1].products.size).isEqualTo(0)
    }
}