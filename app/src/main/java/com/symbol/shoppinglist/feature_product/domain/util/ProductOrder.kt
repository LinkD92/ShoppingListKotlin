package com.symbol.shoppinglist.feature_product.domain.util

import com.symbol.shoppinglist.core.domain.util.OrderType


sealed class ProductsOrder(val orderType: OrderType) {
    class Name(orderType: OrderType): ProductsOrder(orderType)
    class Price(orderType: OrderType): ProductsOrder(orderType)
//    class ShopPositionOrder(orderType: OrderType): ProductsOrder(orderType)
}