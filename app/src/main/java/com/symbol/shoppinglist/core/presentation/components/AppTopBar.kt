package com.symbol.shoppinglist.core.presentation.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.symbol.shoppinglist.Action
import com.symbol.shoppinglist.Error
import com.symbol.shoppinglist.NavigationRoutes
import com.symbol.shoppinglist.R
import com.symbol.shoppinglist.core.presentation.navigation.CategoriesDirections
import com.symbol.shoppinglist.core.presentation.navigation.ProductsDirections
import com.symbol.shoppinglist.core.presentation.navigation.listOfRootRoutes

@Composable
fun AppTopBar(
    navController: NavHostController,
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val route = backStackEntry?.destination?.route
    val showBackButton = !listOfRootRoutes.contains(route)
    val topBarTitle =
        getTopBarTitle(route) { argumentName -> haveArguments(backStackEntry, argumentName) }
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
        },
        elevation = 10.dp,
    )
}

private fun haveArguments(backStackEntry: NavBackStackEntry?, argumentName: String): Boolean {
    val invalidId = NavigationRoutes.Arguments.INVALID_ID
    return backStackEntry?.arguments?.getInt(argumentName) != invalidId
}

@Composable
private fun getTopBarTitle(route: String?, haveArguments: (String) -> Boolean): String {
    return when (route) {
        ProductsDirections.Root.route -> stringResource(id = R.string.products)
        ProductsDirections.AddProduct.route -> {
            val productId = NavigationRoutes.Products.Arguments.ID
            if (haveArguments(productId))
                stringResource(id = R.string.edit_product)
            else
                stringResource(id = R.string.add_product)
        }
        CategoriesDirections.Root.route -> stringResource(id = R.string.categories)
        CategoriesDirections.AddCategory.route -> {
            val categoryId = NavigationRoutes.Categories.Arguments.ID
            if (haveArguments(categoryId))
                stringResource(id = R.string.edit_category)
            else
                stringResource(id = R.string.add_category)
        }
        CategoriesDirections.ColorPicker.route -> stringResource(id = R.string.color_picker)
        else -> Error.LOADING
    }
}