package com.symbol.shoppinglist.feature_product.presentation.display_products

import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_product.domain.model.Product
import com.symbol.shoppinglist.feature_product.domain.util.ProductOrder

sealed class DisplayProductsEvent {
    data class Order(val productOrder: ProductOrder): DisplayProductsEvent()
    data class DeleteProduct(val productId: Int): DisplayProductsEvent()
    data class OnProductLongClick(val product: Product): DisplayProductsEvent()
    object RestoreProduct: DisplayProductsEvent()
    data class ExpandCategory(val category: Category): DisplayProductsEvent()
    data class ChangeProductSelection(val product: Product): DisplayProductsEvent()
}