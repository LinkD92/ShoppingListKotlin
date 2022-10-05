package com.symbol.shoppinglist.ui

import android.util.Log
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.symbol.shoppinglist.Action
import com.symbol.shoppinglist.ui.navigation.BottomNavigationItem


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
//        when (currentScreen) {
//            ScreenName.PRODUCTS -> navController.navigate(ScreenName.ADD_PRODUCT)
//            ScreenName.CATEGORIES -> navController.navigate(ScreenName.ADD_CATEGORY)
//        }
    }) {
        Icon(Icons.Rounded.Add, Action.ADD)
    }
}


@Composable
fun AppBottomNavigation(navController: NavHostController) {
    val listOfItems = listOf(
        BottomNavigationItem.Products,
        BottomNavigationItem.Categories
    )
    BottomNavigation() {
        val currentScreen = navController.currentBackStackEntry?.destination?.route
        listOfItems.forEach { item ->
            BottomNavigationItem(selected = currentScreen == item.screenName,
                onClick = {
                    navController.navigate(item.screenName).also {
                        Log.d("QWAS - AppBottomNavigation:", "${item.screenName}")
                    }
                },
                icon = { Icon(item.icon, item.screenName) },
                label = { Text(text = item.screenName) }
            )
        }

    }
}


@Preview
@Composable
fun ViewPreview() {
    val navController = rememberNavController()
    AppBottomNavigation(navController = navController)
}