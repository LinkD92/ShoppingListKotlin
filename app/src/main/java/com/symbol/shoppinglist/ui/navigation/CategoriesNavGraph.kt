package com.symbol.shoppinglist.ui.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.symbol.shoppinglist.NavGraphs
import com.symbol.shoppinglist.ScreenName
import com.symbol.shoppinglist.ui.categoriesAdd.AddCategory
import com.symbol.shoppinglist.ui.categoriesAdd.AddCategoryViewModel
import com.symbol.shoppinglist.ui.categoriesAdd.ColorPicker
import com.symbol.shoppinglist.ui.categoriesManage.ManageCategories

fun NavGraphBuilder.categoriesNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    navigation(
        startDestination = ScreenName.CATEGORIES,
        route = NavGraphs.CATEGORIES
    ) {
        composable(ScreenName.CATEGORIES) { ManageCategories(modifier) }
        composable(ScreenName.ADD_CATEGORY) { AddCategory(modifier, navController) }
        composable(ScreenName.COLOR_PICKER) { ColorPicker(modifier, navController) }
    }
}