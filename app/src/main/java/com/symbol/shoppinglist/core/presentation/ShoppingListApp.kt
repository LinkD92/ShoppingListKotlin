package com.symbol.shoppinglist.core.presentation.components

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
import com.symbol.shoppinglist.core.presentation.ui.theme.ShoppingListTheme


@Composable
fun ShoppingListApp() {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()


    ShoppingListTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            scaffoldState = scaffoldState,
            topBar = { AppTopBar(navController) },
            floatingActionButton = { AppFab(navController) },
            bottomBar = { AppBottomNavigation(navController) }
        )
        {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                AppNavGraph(
                    navController = navController,
                    snackbarHostState = scaffoldState.snackbarHostState
                )
            }
        }
    }
}

