package com.symbol.shoppinglist.core.presentation.navigation

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun AppNavGraph(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    startDestination: String = BottomNavigationDirection.Products.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        productsNavGraph(navHostController = navController, snackbarHostState = snackbarHostState)
        categoriesNavGraph(navHostController = navController, snackbarHostState = snackbarHostState)
        settingsNavGraph(navHostController = navController, snackbarHostState = snackbarHostState)
    }
}