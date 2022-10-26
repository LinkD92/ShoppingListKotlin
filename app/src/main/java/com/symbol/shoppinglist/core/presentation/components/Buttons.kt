package com.symbol.shoppinglist.core.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.symbol.shoppinglist.Action
import com.symbol.shoppinglist.core.presentation.navigation.CategoriesDirections
import com.symbol.shoppinglist.core.presentation.navigation.ProductsDirections

@Composable
fun AppFab(navController: NavHostController) {
    val currentScreen = navController.currentBackStackEntryAsState().value?.destination?.route
    val addProduct = ProductsDirections.AddProduct.route
    val addCategory = CategoriesDirections.AddCategory.route

    if (!(currentScreen == addProduct || currentScreen == addCategory))
        FloatingActionButton(
            modifier = Modifier,
            elevation = FloatingActionButtonDefaults.elevation(10.dp),
            onClick = {
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
fun ConfirmButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(Icons.Rounded.Check, Action.CHECK)
    }
}