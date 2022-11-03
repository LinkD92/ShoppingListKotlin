package com.symbol.shoppinglist.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.symbol.shoppinglist.R
import com.symbol.shoppinglist.core.data.util.Action
import com.symbol.shoppinglist.core.data.util.Error
import com.symbol.shoppinglist.core.data.util.NavigationRoutes
import com.symbol.shoppinglist.core.presentation.TopBarAction
import com.symbol.shoppinglist.core.presentation.navigation.CategoriesDirections
import com.symbol.shoppinglist.core.presentation.navigation.ProductsDirections
import com.symbol.shoppinglist.core.presentation.navigation.SettingsDirections
import com.symbol.shoppinglist.core.presentation.navigation.listOfRootRoutes


@Composable
fun AppTopBar(
    navController: NavHostController,
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val route = backStackEntry?.destination?.route
    val showBackButton = !listOfRootRoutes.contains(route)
    val topBarDetail =
        getTopBarDetails(route) { argumentName -> haveArguments(backStackEntry, argumentName) }
    TopAppBar(
        title = { Text(text = topBarDetail.title) },
        actions = { TopBarAction() },
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
private fun getTopBarDetails(route: String?, haveArguments: (String) -> Boolean): TopBarDetail {
    return when (route) {
        ProductsDirections.Root.route -> {
            val title = stringResource(id = R.string.products)
            TopBarDetail(title)
        }
        ProductsDirections.AddProduct.route -> {
            val productId = NavigationRoutes.Products.Arguments.ID
            val title = if (haveArguments(productId)) {
                stringResource(id = R.string.edit_product)
            } else {
                stringResource(id = R.string.add_product)
            }
            TopBarDetail(title)
        }
        CategoriesDirections.Root.route -> {
            val title = stringResource(id = R.string.categories)
            TopBarDetail(title)
        }
        CategoriesDirections.AddCategory.route -> {
            val categoryId = NavigationRoutes.Categories.Arguments.ID
            val title = if (haveArguments(categoryId))
                stringResource(id = R.string.edit_category)
            else
                stringResource(id = R.string.add_category)
            TopBarDetail(title)
        }
        CategoriesDirections.ColorPicker.route -> {
            val title = stringResource(id = R.string.color_picker)
            TopBarDetail(title)
        }
        SettingsDirections.Root.route -> {
            val title = stringResource(id = R.string.settings)
            TopBarDetail(title)
        }
        SettingsDirections.DisplayProductsCategoryOrder.route -> {
            val title = mergeSettingsTopBarName(R.string.display_products)
            TopBarDetail(title)
        }
        SettingsDirections.Categories.route -> {
            val title = mergeSettingsTopBarName(R.string.categories)
            TopBarDetail(title)
        }
        SettingsDirections.Products.route -> {
            val title = mergeSettingsTopBarName(R.string.products)
            TopBarDetail(title)
        }
        else -> TopBarDetail(Error.IN_PROGRESS)
    }
}

@Composable
private fun mergeSettingsTopBarName(resource1: Int): String {
    return stringResource(id = R.string.settings) + ": " + stringResource(id = resource1)
}

@Composable
private fun TopBarAction() {
    Icon(Icons.Rounded.MoreVert, null, Modifier.clickable {
        
    })
}

data class TopBarDetail(val title: String, val actions: TopBarAction? = null)