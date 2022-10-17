package com.symbol.shoppinglist.ui.categoriesManage

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.symbol.shoppinglist.FakeListRepository
import com.symbol.shoppinglist.database.local.entities.Category
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

class ManageCategoriesViewModelTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    private lateinit var viewModel: ManageCategoriesViewModel

    @Before
    fun setup(){
        viewModel = ManageCategoriesViewModel(FakeListRepository())
    }
}