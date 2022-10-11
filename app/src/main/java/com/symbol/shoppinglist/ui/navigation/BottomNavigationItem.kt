package com.symbol.shoppinglist.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.ShoppingBasket
import androidx.compose.ui.graphics.vector.ImageVector
import com.symbol.shoppinglist.NavGraphs

sealed class BottomNavigationItem(val screenName: String, val icon: ImageVector) {
    object Products : BottomNavigationItem(NavGraphs.PRODUCTS, Icons.Rounded.ShoppingBasket)
    object Categories : BottomNavigationItem(NavGraphs.CATEGORIES, Icons.Rounded.Category)
}