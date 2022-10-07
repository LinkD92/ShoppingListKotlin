package com.symbol.shoppinglist.ui

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.symbol.shoppinglist.Action
import com.symbol.shoppinglist.ScreenName
import com.symbol.shoppinglist.database.entities.Category
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
        when (currentScreen) {
            ScreenName.PRODUCTS -> navController.navigate(ScreenName.ADD_PRODUCT)
            ScreenName.CATEGORIES -> navController.navigate(ScreenName.ADD_CATEGORY)
        }
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

@Composable
fun ColorSquare(modifier: Modifier = Modifier, category: Category){
    Canvas(
        modifier = modifier
            .size(30.dp)
            .clip(RoundedCornerShape(20))
            .background(Color(category.categoryColor))
    ){}
}


