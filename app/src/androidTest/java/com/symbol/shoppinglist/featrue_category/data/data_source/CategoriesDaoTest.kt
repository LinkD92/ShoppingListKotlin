package com.symbol.shoppinglist.featrue_category.data.data_source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.symbol.shoppinglist.core.data.datasource.ListRoomDatabase
import com.symbol.shoppinglist.feature_category.data.data_source.CategoriesDao
import com.symbol.shoppinglist.feature_category.domain.model.Category
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
class CategoriesDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ListRoomDatabase
    private lateinit var dao: CategoriesDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ListRoomDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.categoriesDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertCategory() = runTest {
        // Given
        val category1 = Category("cat1", 100, false, 1)

        // When
        dao.insertCategory(category1)

        // Then
        dao.getAllCategories().test {
            val list = awaitItem()
            assertThat(list).contains(category1)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun getAllCategories() = runTest {
        //Given
        val category1 = Category("cat1", 100, false, 1)
        val category2 = Category("cat2", 1000, false, 2)
        val category3 = Category("cat3", 1000, false, 3)

        //When
        dao.insertCategory(category1)
        dao.insertCategory(category2)
        dao.insertCategory(category3)

        //Then
        dao.getAllCategories().test {
            val list = awaitItem()
            assertThat(list.size).isEqualTo(3)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun getCategory() = runTest {
        // Given
        val category1 = Category("cat1", 100, false, 1)
        dao.insertCategory(category1)

        // When
        val result = dao.getCategory(1)

        // Then
        assertThat(result).isEqualTo(category1)
    }

    @Test
    fun deleteCategory() = runTest {
        // Given
        val category1 = Category("cat1", 100, false, 1)
        dao.insertCategory(category1)

        // When
        dao.deleteCategory(category1)

        // Then
        dao.getAllCategories().test {
            val list = awaitItem()
            assertThat(list.size).isEqualTo(0)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun categoryNameCount() = runTest {
        // Given
        val name = "name"
        val category1 = Category(name, 100, false, 1)
        dao.insertCategory(category1)

        // When
        val result = dao.categoryNameCount(name)

        // Then
        assertThat(result).isEqualTo(1)
    }
}