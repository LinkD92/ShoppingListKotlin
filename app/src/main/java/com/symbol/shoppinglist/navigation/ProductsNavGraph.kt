package com.symbol.shoppinglist.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.symbol.shoppinglist.NavigationRoutes
import com.symbol.shoppinglist.product.DisplayProducts
import com.symbol.shoppinglist.ui.productAdd.AddProduct

sealed class ProductsDirections(val route: String) {
    object Root : ProductsDirections(NavigationRoutes.Products.ROOT)
    object AddProduct : ProductsDirections("${NavigationRoutes.Products.ADD_PRODUCT}?productName={productName}"){
        fun passProductName(name: String):String{
            return "${NavigationRoutes.Products.ADD_PRODUCT}?productName=$name"
        }
    }
}

fun NavGraphBuilder.productsNavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
) {
    navigation(
        startDestination = ProductsDirections.Root.route,
        route = BottomNavigationDirection.Products.route
    ) {
        composable(ProductsDirections.Root.route) {
            DisplayProducts(modifier, navHostController)
        }
        composable(ProductsDirections.AddProduct.route,
            arguments = listOf(
                navArgument("productName") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
            AddProduct(modifier)
        }
    }
}