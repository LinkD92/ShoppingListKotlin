package com.symbol.shoppinglist

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.symbol.shoppinglist.ui.navigation.AppBottomNavigation
import com.symbol.shoppinglist.ui.AppFab
import com.symbol.shoppinglist.ui.AppTopBar
import com.symbol.shoppinglist.ui.navigation.AppNavGraph
import com.symbol.shoppinglist.ui.theme.ShoppingListTheme


@Composable
fun ShoppingListApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route ?: ScreenName.PRODUCTS

    ShoppingListTheme {
        Scaffold(
            topBar = { AppTopBar(currentScreen, navController) },
            floatingActionButton = { AppFab(navController) },
            bottomBar = { AppBottomNavigation(navController) }
        ) {
            it
            AppNavGraph(modifier, navController)
        }
    }
}

