package com.symbol.shoppinglist.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String = BottomNavigationDirection.Products.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        productsNavGraph(navHostController = navController)
        categoriesNavGraph(navHostController =  navController)
    }
}