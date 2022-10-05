package com.symbol.shoppinglist.ui

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.symbol.shoppinglist.Action
import com.symbol.shoppinglist.ScreenName
import com.symbol.shoppinglist.addProduct.AddProductScreen
import com.symbol.shoppinglist.product.DisplayProducts


@Composable
fun AppTopBar(
    screenName: String,
    navController: NavHostController,
) {
    val backStackEntry = navController.previousBackStackEntry
    TopAppBar(
        title = { Text(text = screenName) },
        navigationIcon = {
            if (backStackEntry != null) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = Action.BACK)
                }
            }
        }
    )
}

@Composable
fun AppFab(navController: NavHostController) {
    val currentScreen = navController.currentBackStackEntryAsState().value?.destination?.route
    FloatingActionButton(onClick = {
        if (currentScreen != ScreenName.ADD_PRODUCT) {
            navController.navigate(ScreenName.ADD_PRODUCT)
        }
    }) {
        Icon(Icons.Rounded.Add, Action.ADD)
    }
}


@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController,
        startDestination = ScreenName.PRODUCTS
    ) {
        composable(ScreenName.PRODUCTS) { DisplayProducts(navController) }
        composable(ScreenName.ADD_PRODUCT) { AddProductScreen(navController) }
    }
}