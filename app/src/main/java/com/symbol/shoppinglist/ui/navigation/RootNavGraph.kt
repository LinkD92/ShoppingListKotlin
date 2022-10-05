package com.symbol.shoppinglist.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.symbol.shoppinglist.NavGraphs

@Composable
fun RootNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val startDestination by rememberSaveable {
        mutableStateOf(NavGraphs.PRODUCTS)
    }
    NavHost(
        navController = navController,
        startDestination = startDestination,
        route = NavGraphs.ROOT
    ) {
        productsNavGraph(modifier, navController)
        categoriesNavGraph(modifier, navController)
    }
}