package com.symbol.shoppinglist.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.symbol.shoppinglist.Action
import com.symbol.shoppinglist.Error
import com.symbol.shoppinglist.NavigationRoutes
import com.symbol.shoppinglist.R
import com.symbol.shoppinglist.navigation.CategoriesDirections
import com.symbol.shoppinglist.navigation.ProductsDirections
import com.symbol.shoppinglist.navigation.listOfRootRoutes


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
fun LabelAndPlaceHolder(
    modifier: Modifier = Modifier,
    value: String,
    labelTitle: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    onValueChange: (String) -> Unit
) {
    TextField(
        modifier = modifier,
        keyboardOptions = keyboardOptions,
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = labelTitle) },
        placeholder = { Text(text = labelTitle) },
    )
}

@Composable
fun ConfirmButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        modifier = modifier.padding(horizontal = 10.dp, vertical = 5.dp),
        onClick = onClick
    ) {
        Icon(Icons.Rounded.Check, Action.CHECK)
    }
}