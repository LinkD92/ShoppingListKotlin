package com.symbol.shoppinglist.core.presentation

import androidx.lifecycle.ViewModel
import com.symbol.shoppinglist.feature_product.domain.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    productsRepository: ProductsRepository
): ViewModel(){

    val products = productsRepository.getAllProducts()
}