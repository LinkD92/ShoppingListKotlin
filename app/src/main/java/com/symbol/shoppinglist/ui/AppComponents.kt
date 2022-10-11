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
import com.symbol.shoppinglist.Error
import com.symbol.shoppinglist.NavigationRoutes
import com.symbol.shoppinglist.TopBarName
import com.symbol.shoppinglist.database.entities.Category
import com.symbol.shoppinglist.navigation.CategoriesDirections
import com.symbol.shoppinglist.navigation.ProductsDirections
import com.symbol.shoppinglist.navigation.listOfRootRoutes


@Composable
fun AppTopBar(
    navController: NavHostController,
) {
    val backStackEntry = navController.currentBackStackEntry
    val showBackButton = !listOfRootRoutes.contains(backStackEntry?.destination?.route)
    val haveArguments = (backStackEntry?.destination?.arguments?.size) != 0
    val topBarTitle = getTopBarTitle(backStackEntry?.destination?.route, haveArguments)
    TopAppBar(
        title = { Text(text = topBarTitle) },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = Action.BACK)
                }
            }
        }
    )
}

private fun getTopBarTitle(route: String?, haveArguments: Boolean): String {
    Log.d("QWAS - getTopBarTitle:", "$route")
    Log.d("QWAS - getTopBarTitle:", "$haveArguments")
    return when (route) {
        ProductsDirections.Root.route -> TopBarName.PRODUCTS
        ProductsDirections.AddProduct.route ->{
            if(haveArguments) TopBarName.EDIT_PRODUCT else TopBarName.ADD_PRODUCT
        }
        CategoriesDirections.Root.route -> TopBarName.CATEGORIES
        CategoriesDirections.AddCategory.route -> TopBarName.ADD_CATEGORY
        CategoriesDirections.ColorPicker.route -> TopBarName.COLOR_PICKER
        else -> Error.ERROR
    }
}


@Composable
fun AppFab(navController: NavHostController) {
    val currentScreen = navController.currentBackStackEntryAsState().value?.destination?.route
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
fun ColorSquare(modifier: Modifier = Modifier, color: Long) {
    Canvas(
        modifier = modifier
            .size(30.dp)
            .clip(RoundedCornerShape(20))
            .background(Color(color))
    ) {}
}
