package com.symbol.shoppinglist.feature_product.presentation.display_products.components

import com.symbol.shoppinglist.feature_product.domain.model.Product
import kotlinx.coroutines.flow.StateFlow

sealed class CategoryCardMenuEvent {
    data class CheckUncheckAll(val products: List<StateFlow<Product>>): CategoryCardMenuEvent()
}