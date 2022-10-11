package com.symbol.shoppinglist.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.symbol.shoppinglist.NavigationRoutes

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = BottomNavigationDirection.Products.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        productsNavGraph(modifier, navController)
        categoriesNavGraph(modifier, navController)
    }
}