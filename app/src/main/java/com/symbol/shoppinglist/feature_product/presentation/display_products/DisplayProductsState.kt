package com.symbol.shoppinglist.feature_product.presentation.display_products

import com.symbol.shoppinglist.core.domain.util.OrderType
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.util.CategoryOrderType
import com.symbol.shoppinglist.feature_product.domain.model.Product
import com.symbol.shoppinglist.feature_product.domain.util.ProductOrder

data class DisplayProductsState(
    var longPressProduct: Product? = null,
    val productsOfCategory: Map<Category, List<Product>> = emptyMap(),
    val categories: List<Category> = emptyList(),
    val categoryOrderType: CategoryOrderType = CategoryOrderType.NAME,
    val productOrder: ProductOrder = ProductOrder.Name(OrderType.Ascending),
)