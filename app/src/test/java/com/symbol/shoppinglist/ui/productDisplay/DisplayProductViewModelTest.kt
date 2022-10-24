package com.symbol.shoppinglist.ui.productDisplay

import com.symbol.shoppinglist.FakeListRepository
import com.symbol.shoppinglist.feature_product.presentation.display_products.DisplayProductViewModel
import org.junit.Before

class DisplayProductViewModelTest {

    private lateinit var viewModel: DisplayProductViewModel

    @Before
    fun setUp() {
        viewModel = DisplayProductViewModel(FakeListRepository())
    }

}