package com.symbol.shoppinglist.product

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.symbol.shoppinglist.ui.theme.ShoppingListTheme

@Composable
fun AddProductView(navController: NavController){
    ShoppingListTheme {
        Surface {
            Column {

            }
            Text(text = "AddProduct")
        }
    }
}