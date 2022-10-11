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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.symbol.shoppinglist.Action
import com.symbol.shoppinglist.database.entities.Category
import com.symbol.shoppinglist.ui.navigation.CategoriesDirections
import com.symbol.shoppinglist.ui.navigation.ProductsDirections


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
    Log.d("QWAS - AppFab:", "$currentScreen")
    FloatingActionButton(onClick = {
        when (currentScreen) {
            ProductsDirections.Root.route -> {
                navController.navigate(ProductsDirections.AddProduct.route)
            }
            CategoriesDirections.Root.route -> {
                navController.navigate(CategoriesDirections.AddCategory.route)
            }
        }
    }) {
        Icon(Icons.Rounded.Add, Action.ADD)
    }
}


@Composable
fun ColorSquare(modifier: Modifier = Modifier, category: Category) {
    Canvas(
        modifier = modifier
            .size(30.dp)
            .clip(RoundedCornerShape(20))
            .background(Color(category.categoryColor))
    ) {}
}
