package com.symbol.shoppinglist.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.symbol.shoppinglist.*
import com.symbol.shoppinglist.R
import com.symbol.shoppinglist.navigation.CategoriesDirections
import com.symbol.shoppinglist.navigation.ProductsDirections
import com.symbol.shoppinglist.navigation.listOfRootRoutes


@Composable
fun AppTopBar(
    navController: NavHostController,
) {
    val productName = NavigationRoutes.Products.Arguments.PRODUCT_NAME
    val backStackEntry by navController.currentBackStackEntryAsState()
    val route = backStackEntry?.destination?.route
    val showBackButton = !listOfRootRoutes.contains(route)
    val haveArguments = backStackEntry?.arguments?.getString(productName) != null
    val topBarTitle = getTopBarTitle(route, haveArguments)
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

@Composable
private fun getTopBarTitle(route: String?, haveArguments: Boolean = false): String {
    return when (route) {
        ProductsDirections.Root.route -> stringResource(id = R.string.products)
        ProductsDirections.AddProduct.route -> {
            if (haveArguments)
                stringResource(id = R.string.edit_product)
            else
                stringResource(id = R.string.add_product)
        }
        CategoriesDirections.Root.route -> stringResource(id = R.string.categories)
        CategoriesDirections.AddCategory.route -> stringResource(id = R.string.add_category)
        CategoriesDirections.ColorPicker.route -> stringResource(id = R.string.color_picker)
        else -> Error.LOADING
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
