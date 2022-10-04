package com.symbol.shoppinglist

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.symbol.shoppinglist.addProduct.AddProductView
import com.symbol.shoppinglist.product.DisplayProducts
import com.symbol.shoppinglist.ui.AppFab
import com.symbol.shoppinglist.ui.AppTopBar
import com.symbol.shoppinglist.ui.NavigationGraph


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ShoppingListScreen() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route ?: ScreenName.PRODUCTS

    Scaffold(
        topBar = { AppTopBar(currentScreen, navController) },
        floatingActionButton = { AppFab(navController) }
    ) {
        NavigationGraph(navController)
    }
}

