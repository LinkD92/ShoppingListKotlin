package com.symbol.shoppinglist.core.presentation.navigation

import androidx.compose.material.SnackbarHostState
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.symbol.shoppinglist.NavigationRoutes
import com.symbol.shoppinglist.feature_category.presentation.add_edit_category.components.AddCategory
import com.symbol.shoppinglist.feature_category.presentation.add_edit_category.components.ColorPicker
import com.symbol.shoppinglist.feature_category.presentation.manage_categories.components.ManageCategories

private const val categoryId = NavigationRoutes.Categories.Arguments.ID

sealed class CategoriesDirections(val route: String) {
    object Root : CategoriesDirections(NavigationRoutes.Categories.ROOT)
    object AddCategory : CategoriesDirections(
        "${NavigationRoutes.Categories.ADD_CATEGORY}" +
                "${NavigationRoutes.addArgumentName(categoryId)}"
    ) {
        fun passArgument(id: Int): String {
            return "${NavigationRoutes.Categories.ADD_CATEGORY}${
                NavigationRoutes.addArgument(categoryId, id)
            }"
        }
    }

    object ColorPicker : CategoriesDirections(NavigationRoutes.Categories.COLOR_PICKER)
}


fun NavGraphBuilder.categoriesNavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    navigation(
        startDestination = CategoriesDirections.Root.route,
        route = BottomNavigationDirection.Categories.route
    ) {
        composable(CategoriesDirections.Root.route) {
            ManageCategories(modifier, navHostController)
        }
        composable(
            CategoriesDirections.AddCategory.route,
            arguments = listOf(
                navArgument(categoryId) {
                    type = NavType.IntType
                    defaultValue = NavigationRoutes.Arguments.INVALID_ID
                }
            )
        ) { AddCategory(modifier, navHostController, snackbarHostState) }
        composable(CategoriesDirections.ColorPicker.route) {
            ColorPicker(modifier, navHostController)
        }
    }
}