package com.symbol.shoppinglist.core.presentation.navigation

import androidx.compose.material.SnackbarHostState
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.symbol.shoppinglist.NavigationRoutes
import com.symbol.shoppinglist.feature_product.presentation.add_edit_product.components.AddEditProduct
import com.symbol.shoppinglist.ui.productDisplay.DisplayProducts

private const val productId = NavigationRoutes.Products.Arguments.ID

sealed class ProductsDirections(val route: String) {
    object Root : ProductsDirections(NavigationRoutes.Products.ROOT)
    object AddProduct : ProductsDirections(
        "${NavigationRoutes.Products.ADD_PRODUCT}" +
                "${NavigationRoutes.addArgumentName(productId)}"
    ) {
        fun passArgument(id: Int): String {
            return "${NavigationRoutes.Products.ADD_PRODUCT}${
                NavigationRoutes.addArgument(productId, id)
            }"
        }
    }
}

fun NavGraphBuilder.productsNavGraph(
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    navigation(
        startDestination = ProductsDirections.Root.route,
        route = BottomNavigationDirection.Products.route
    ) {
        composable(ProductsDirections.Root.route) {
            DisplayProducts(navHostController = navHostController, snackbarHostState = snackbarHostState)
        }
        composable(
            ProductsDirections.AddProduct.route,
            arguments = listOf(
                navArgument(productId) {
                    type = NavType.IntType
                    defaultValue = NavigationRoutes.Arguments.INVALID_ID
                }
            )
        ) {
            AddEditProduct(snackbarHostState = snackbarHostState)
        }
    }
}