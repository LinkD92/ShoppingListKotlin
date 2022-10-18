package com.symbol.shoppinglist.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
        elevation = 10.dp,
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

@Composable
fun LabelAndPlaceHolder(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = "Your Label") },
        placeholder = { Text(text = "Your Placeholder/Hint") },
    )
}

@Composable
fun AddButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(Icons.Rounded.Check, Action.CHECK)
    }
}