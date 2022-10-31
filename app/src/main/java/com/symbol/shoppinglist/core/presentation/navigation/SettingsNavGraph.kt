package com.symbol.shoppinglist.core.presentation.navigation

import androidx.compose.material.SnackbarHostState
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.symbol.shoppinglist.core.data.util.NavigationRoutes
import com.symbol.shoppinglist.feature_settings.presentation.display_products.SettingsDisplayProducts
import com.symbol.shoppinglist.feature_settings.presentation.Settings
import com.symbol.shoppinglist.feature_settings.presentation.categories.SettingsCategories
import com.symbol.shoppinglist.feature_settings.presentation.products.SettingsProducts

sealed class SettingsDirections(val route: String) {
    object Root : SettingsDirections(NavigationRoutes.Settings.ROOT)
    object DisplayProducts : SettingsDirections(NavigationRoutes.Settings.DISPLAY_PRODUCTS)
    object Categories : SettingsDirections(NavigationRoutes.Settings.CATEGORIES)
    object Products : SettingsDirections(NavigationRoutes.Settings.PRODUCTS)
}


fun NavGraphBuilder.settingsNavGraph(
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState,
) {
    navigation(
        startDestination = SettingsDirections.Root.route,
        route = BottomNavigationDirection.Categories.route
    ) {
        composable(SettingsDirections.Root.route) {
            Settings(
                navHostController = navHostController,
                snackbarHostState = snackbarHostState
            )
        }
        composable(SettingsDirections.DisplayProducts.route) {
            SettingsDisplayProducts(
                    navHostController = navHostController,
                    snackbarHostState = snackbarHostState
                )
        }
        composable(SettingsDirections.Categories.route) {
            SettingsCategories(
                navHostController = navHostController,
                snackbarHostState = snackbarHostState
            )
        }
        composable(SettingsDirections.Products.route){
            SettingsProducts(
                navHostController = navHostController,
                snackbarHostState = snackbarHostState
            )
        }
    }
}