package com.symbol.shoppinglist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.symbol.shoppinglist.core.presentation.navigation.AppBottomNavigation
import com.symbol.shoppinglist.core.presentation.navigation.AppNavGraph
import com.symbol.shoppinglist.ui.AppFab
import com.symbol.shoppinglist.ui.AppTopBar
import com.symbol.shoppinglist.core.presentation.ui.theme.ShoppingListTheme


@Composable
fun ShoppingListApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()

    ShoppingListTheme {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            scaffoldState = scaffoldState,
            topBar = { AppTopBar(navController) },
            floatingActionButton = { AppFab(navController) },
            bottomBar = { AppBottomNavigation(navController) }
        ) {
            Box(modifier = Modifier.padding(it)
                .fillMaxSize()){
                AppNavGraph(navController = navController, scaffoldState.snackbarHostState)
            }
        }
    }
}

