package com.symbol.shoppinglist.feature_category.domain.util

import com.symbol.shoppinglist.core.domain.util.OrderType

sealed class CategoriesOrder(orderType: OrderType) {
    class Name(orderType: OrderType): CategoriesOrder(orderType)
}