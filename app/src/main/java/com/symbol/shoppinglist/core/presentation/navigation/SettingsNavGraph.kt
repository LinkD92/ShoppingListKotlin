package com.symbol.shoppinglist.core.presentation.navigation

import androidx.compose.material.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.symbol.shoppinglist.core.data.util.NavigationRoutes
import com.symbol.shoppinglist.feature_settings.presentation.categories.SettingsCategories
import com.symbol.shoppinglist.feature_settings.presentation.display_products.SettingsDisplayProductsCategoryOrder
import com.symbol.shoppinglist.feature_settings.presentation.products.SettingsProducts
import com.symbol.shoppinglist.feature_settings.presentation.settings.Settings

sealed class SettingsDirections(val route: String) {
    object Root : SettingsDirections(NavigationRoutes.Settings.ROOT)
    object DisplayProductsCategoryOrder :
        SettingsDirections(NavigationRoutes.Settings.DISPLAY_PRODUCTS_CATEGORY_ORDER)
    object Categories : SettingsDirections(NavigationRoutes.Settings.CATEGORIES)
    object Products : SettingsDirections(NavigationRoutes.Settings.PRODUCTS)
}


fun NavGraphBuilder.settingsNavGraph(
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState,
) {
    navigation(
        startDestination = SettingsDirections.Root.route,
        route = BottomNavigationDirection.Settings.route
    ) {
        composable(SettingsDirections.Root.route) {
            Settings(
                navHostController = navHostController,
                snackbarHostState = snackbarHostState
            )
        }
        composable(SettingsDirections.DisplayProductsCategoryOrder.route) {
            SettingsDisplayProductsCategoryOrder(
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
        composable(SettingsDirections.Products.route) {
            SettingsProducts(
                navHostController = navHostController,
                snackbarHostState = snackbarHostState
            )
        }
    }
}