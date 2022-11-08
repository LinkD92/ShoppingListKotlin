package com.symbol.shoppinglist.feature_product.domain.util

import com.symbol.shoppinglist.core.domain.util.OrderType


sealed class ProductOrder(val orderType: OrderType) {
    class Name(orderType: OrderType): ProductOrder(orderType)
    class Price(orderType: OrderType): ProductOrder(orderType)
//    class ShopPositionOrder(orderType: OrderType): ProductsOrder(orderType)
}