package com.symbol.shoppinglist.product

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.symbol.shoppinglist.ScreenName
import com.symbol.shoppinglist.displayProducts.DisplayProductViewModel
import com.symbol.shoppinglist.ui.theme.ShoppingListTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DisplayProducts (navController: NavController, viewModel: DisplayProductViewModel = hiltViewModel()) {
    Column() {
        Text(text = viewModel.test)
        Text(text = viewModel.test)
        Text(text = viewModel.test)

    }
}



