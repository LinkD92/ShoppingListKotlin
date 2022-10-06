package com.symbol.shoppinglist.ui.navigation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.symbol.shoppinglist.MainActivity
import com.symbol.shoppinglist.NavGraphs
import com.symbol.shoppinglist.ui.categoriesAdd.AddCategoryViewModel

@Composable
fun RootNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val localContext = LocalContext.current
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