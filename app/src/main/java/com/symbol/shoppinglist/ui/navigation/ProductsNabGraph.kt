package com.symbol.shoppinglist.ui.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.symbol.shoppinglist.NavGraphs
import com.symbol.shoppinglist.ScreenName
import com.symbol.shoppinglist.product.DisplayProducts
import com.symbol.shoppinglist.ui.productAdd.AddProduct

fun NavGraphBuilder.productsNavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    val currentScreen =
        navHostController.currentBackStackEntry?.destination?.route ?: ScreenName.PRODUCTS
    navigation(
        startDestination = currentScreen,
        route = NavGraphs.PRODUCTS
    ) {
        composable(ScreenName.PRODUCTS) { DisplayProducts(modifier) }
        composable(ScreenName.ADD_PRODUCT) { AddProduct(modifier) }
    }
}