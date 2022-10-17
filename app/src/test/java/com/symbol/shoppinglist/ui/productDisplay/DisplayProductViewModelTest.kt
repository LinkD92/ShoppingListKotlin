package com.symbol.shoppinglist.ui.productDisplay

import com.symbol.shoppinglist.FakeListRepository
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class DisplayProductViewModelTest {

    private lateinit var viewModel: DisplayProductViewModel

    @Before
    fun setUp() {
        viewModel = DisplayProductViewModel(FakeListRepository())
    }

}