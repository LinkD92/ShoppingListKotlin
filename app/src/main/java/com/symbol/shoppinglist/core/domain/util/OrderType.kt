package com.symbol.shoppinglist.feature_product.domain.util


sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}