package com.symbol.shoppinglist.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.symbol.shoppinglist.NavigationRoutes
import com.symbol.shoppinglist.ui.categoriesAdd.AddCategory
import com.symbol.shoppinglist.ui.categoriesAdd.ColorPicker
import com.symbol.shoppinglist.ui.categoriesManage.ManageCategories

sealed class CategoriesDirections(val route: String) {
    object Root : CategoriesDirections(NavigationRoutes.Categories.ROOT)
    object AddCategory : CategoriesDirections(NavigationRoutes.Categories.ADD_CATEGORY)
    object ColorPicker : CategoriesDirections(NavigationRoutes.Categories.COLOR_PICKER)
}


fun NavGraphBuilder.categoriesNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    navigation(
        startDestination = CategoriesDirections.Root.route,
        route = BottomNavigationDirection.Categories.route
    ) {
        composable(CategoriesDirections.Root.route) { ManageCategories(modifier) }
        composable(CategoriesDirections.AddCategory.route) { AddCategory(modifier, navController) }
        composable(CategoriesDirections.ColorPicker.route) { ColorPicker(modifier, navController) }
    }
}