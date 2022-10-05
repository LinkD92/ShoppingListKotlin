package com.symbol.shoppinglist

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.symbol.shoppinglist.ui.AppFab
import com.symbol.shoppinglist.ui.AppTopBar
import com.symbol.shoppinglist.ui.NavigationGraph
import com.symbol.shoppinglist.ui.theme.ShoppingListTheme


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ShoppingListApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route ?: ScreenName.PRODUCTS

    ShoppingListTheme {
        Scaffold(
            topBar = { AppTopBar(currentScreen, navController) },
            floatingActionButton = { AppFab(navController) }
        ) {
            NavigationGraph(navController)
        }
    }
}

