package com.symbol.shoppinglist.ui.productDisplay

import com.symbol.shoppinglist.FakeListRepository
import com.symbol.shoppinglist.feature_product.presentation.display_products.DisplayProductsViewModel
import org.junit.Before

class DisplayProductsViewModelTest {

    private lateinit var viewModel: DisplayProductsViewModel

    @Before
    fun setUp() {
        viewModel = DisplayProductsViewModel(FakeListRepository())
    }

}