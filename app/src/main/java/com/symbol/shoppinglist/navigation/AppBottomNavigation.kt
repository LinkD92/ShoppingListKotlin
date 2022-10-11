package com.symbol.shoppinglist.ui.navigation

import android.util.Log
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.ShoppingBasket
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.symbol.shoppinglist.NavigationRoutes


sealed class BottomNavigationDirection(val route: String, val icon: ImageVector) {
    object Products : BottomNavigationDirection(
        NavigationRoutes.NavGraphs.PRODUCTS,
        Icons.Rounded.ShoppingBasket
    )

    object Categories : BottomNavigationDirection(
        NavigationRoutes.NavGraphs.CATEGORIES,
        Icons.Rounded.Category
    )
}

@Composable
fun AppBottomNavigation(navController: NavHostController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: BottomNavigationDirection.Products
//    val listOfNavigationItems = BottomNavigationDirection::class.nestedClasses.map {
//        it.objectInstance as BottomNavigationDirection
//    }

    val listOfNavigationItems = listOf(
        BottomNavigationDirection.Products,
        BottomNavigationDirection.Categories
    )
    BottomNavigation() {
        listOfNavigationItems.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                onClick = {
                    Log.d("QWAS - AppBottomNavigation:", "CUR: ${currentRoute.toString()}")
                    Log.d("QWAS - AppBottomNavigation:", " ROUT: ${item.route}")
                    if (currentRoute.toString().startsWith(item.route)) {
                        navController.navigate(findBottomNavRootRoute(item.route)) {
                            popUpTo(findStartDestination(navController.graph).id)
                        }
                    } else if(currentRoute != item.route){
                        navController.navigate(item.route){
                            launchSingleTop = true
                            restoreState = true
                            val startDestination = findStartDestination(navController.graph)
                            popUpTo(startDestination.id){
                                saveState = true
                            }
                        }
                        Log.d("QWAS - AppBottomNavigation:", "ELSE")
                    }
                },
                icon = { Icon(item.icon, item.route) },
                label = { Text(text = item.route) }
            )
        }

    }
}

private fun findBottomNavRootRoute(bottomNavigationDirection: String): String {
    return when (bottomNavigationDirection) {
        BottomNavigationDirection.Products.route -> ProductsDirections.Root.route
        else -> CategoriesDirections.Root.route
//        BottomNavigationDirection.Products.route -> CategoriesDirections.Root.route
    }
}

private tailrec fun findStartDestination(graph: NavDestination): NavDestination {
    return if (graph is NavGraph) findStartDestination(graph.startDestination!!) else graph
}

private val NavGraph.startDestination: NavDestination?
    get() = findNode(startDestinationId)
