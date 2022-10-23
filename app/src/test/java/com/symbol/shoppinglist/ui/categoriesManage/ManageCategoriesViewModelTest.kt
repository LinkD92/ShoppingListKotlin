package com.symbol.shoppinglist.ui.categoriesManage

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.symbol.shoppinglist.FakeListRepository
import com.symbol.shoppinglist.feature_category.presentation.manage_categories.ManageCategoriesViewModel
import org.junit.Before
import org.junit.Rule

class ManageCategoriesViewModelTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    private lateinit var viewModel: ManageCategoriesViewModel

    @Before
    fun setup(){
        viewModel = ManageCategoriesViewModel(FakeListRepository())
    }
}