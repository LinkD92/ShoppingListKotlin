package com.symbol.shoppinglist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.symbol.shoppinglist.navigation.AppBottomNavigation
import com.symbol.shoppinglist.ui.AppFab
import com.symbol.shoppinglist.ui.AppTopBar
import com.symbol.shoppinglist.navigation.AppNavGraph
import com.symbol.shoppinglist.ui.theme.ShoppingListTheme


@Composable
fun ShoppingListApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    ShoppingListTheme {
        Scaffold(
            topBar = { AppTopBar(navController) },
            floatingActionButton = { AppFab(navController) },
            bottomBar = { AppBottomNavigation(navController) }
        ) {
            Box(modifier = Modifier.padding(it)
                .fillMaxSize()){
                AppNavGraph(navController = navController)
            }
        }
    }
}

