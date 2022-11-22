package com.symbol.shoppinglist.core.presentation.navigation

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.symbol.shoppinglist.core.domain.MainActivityActionEvent

@Composable
fun AppNavGraph(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    startDestination: String = BottomNavigationDirection.Products.route,
    createFile: (MainActivityActionEvent) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        productsNavGraph(navHostController = navController, snackbarHostState = snackbarHostState)
        categoriesNavGraph(navHostController = navController, snackbarHostState = snackbarHostState)
        settingsNavGraph(
            navHostController = navController,
            snackbarHostState = snackbarHostState,
            createFile = {event -> createFile(event)}
        )
    }
}