package com.symbol.shoppinglist.ui.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.symbol.shoppinglist.NavigationRoutes
import com.symbol.shoppinglist.product.DisplayProducts
import com.symbol.shoppinglist.ui.productAdd.AddProduct

sealed class ProductsDirections(val route: String) {
    object Root : ProductsDirections(NavigationRoutes.Products.ROOT)
    object AddProduct : ProductsDirections(NavigationRoutes.Products.ADD_PRODUCT)
}

fun NavGraphBuilder.productsNavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
) {
    navigation(
        startDestination = ProductsDirections.Root.route,
        route = BottomNavigationDirection.Products.route
    ) {
        composable(ProductsDirections.Root.route) { DisplayProducts(modifier, navHostController) }
        composable(ProductsDirections.AddProduct.route) { AddProduct(modifier) }
    }
}