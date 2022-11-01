package com.symbol.shoppinglist.feature_category.domain.util

import com.symbol.shoppinglist.core.domain.util.OrderType

sealed class CategoryOrder(val orderType: OrderType) {
    class Name(orderType: OrderType): CategoryOrder(orderType)
    class Custom(orderType: OrderType): CategoryOrder(orderType)
//    class TotalPrice(orderType: OrderType): CategoriesOrder(orderType)
}