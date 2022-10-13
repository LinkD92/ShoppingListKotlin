package com.symbol.shoppinglist.database.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.symbol.shoppinglist.database.local.entities.Category
import getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
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
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ListDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.listDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun addCategory() = runBlockingTest {
        val category = Category("cat1", 100, false, 1)
        dao.addCategory(category)

        val allCategories = dao.getAllCategories().getOrAwaitValue()
        assertThat(allCategories).contains(category)
    }

}