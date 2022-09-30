package com.symbol.shoppinglist.product

import android.annotation.SuppressLint
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.symbol.shoppinglist.ui.theme.ShoppingListTheme
import com.symbol.shoppinglist.viewcomponents.FloatingActionButtonComponent

@Preview
@Composable
fun mainScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "productList") {
        composable(route = "productList") { ProductListView(navController) }
        composable(route = "addProduct") { AddProductView(navController) }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProductListView(navController: NavController) {
    ShoppingListTheme {
        Surface {
            Scaffold(
                floatingActionButton = {
                    FloatingActionButtonComponent {
                        navController.navigate("addProduct")
                    }
                },
                bottomBar = {},
                topBar = {}) {

            }
            Text(
                text = "firstSCreen",
                color = MaterialTheme.colors.primary
            )
        }
    }
}
