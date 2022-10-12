package com.symbol.shoppinglist.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.symbol.shoppinglist.NavigationRoutes
import com.symbol.shoppinglist.ui.categoriesAdd.AddCategory
import com.symbol.shoppinglist.ui.categoriesAdd.ColorPicker
import com.symbol.shoppinglist.ui.categoriesManage.ManageCategories

private const val categoryId = NavigationRoutes.Products.Arguments.ID

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
    navController: NavHostController,
) {
    navigation(
        startDestination = CategoriesDirections.Root.route,
        route = BottomNavigationDirection.Categories.route
    ) {
        composable(CategoriesDirections.Root.route) { ManageCategories(modifier, navController) }
        composable(CategoriesDirections.AddCategory.route,
            arguments = listOf(
                navArgument(categoryId){
                    type = NavType.IntType
                    defaultValue = NavigationRoutes.Arguments.INVALID_ID
                }
            )
        ) { AddCategory(modifier, navController) }
        composable(CategoriesDirections.ColorPicker.route) { ColorPicker(modifier, navController) }
    }
}