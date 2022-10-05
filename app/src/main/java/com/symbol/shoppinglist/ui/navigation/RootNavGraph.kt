package com.symbol.shoppinglist.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.symbol.shoppinglist.NavGraphs
import com.symbol.shoppinglist.ScreenName
import com.symbol.shoppinglist.ui.categoriesAdd.AddCategory
import com.symbol.shoppinglist.ui.categoriesAdd.ColorPicker
import com.symbol.shoppinglist.ui.categoriesManage.ManageCategories

@Composable
fun RootNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val startDestination by rememberSaveable {
        mutableStateOf(NavGraphs.PRODUCTS)
    }
    NavHost(
        navController = navController,
        startDestination = ScreenName.CATEGORIES,
        route = NavGraphs.ROOT
    ) {
        productsNavGraph(modifier, navController)
        composable(ScreenName.CATEGORIES) { ManageCategories(modifier) }
        composable(ScreenName.ADD_CATEGORY) { AddCategory(modifier) }
        composable(ScreenName.COLOR_PICKER) { ColorPicker(modifier) }
//        categoriesNavGraph(modifier, navController)
    }
}