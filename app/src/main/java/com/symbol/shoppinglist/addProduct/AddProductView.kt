package com.symbol.shoppinglist.product

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.symbol.shoppinglist.addProduct.AddProductViewModel
import com.symbol.shoppinglist.data.Product
import com.symbol.shoppinglist.ui.theme.ShoppingListTheme

@Composable
fun AddProductView(
) {
    val product = Product("testowy", "category", 10)
    ShoppingListTheme {
        Surface {
            Column {
                Text(text = "AddProduct")
                Button(onClick = {
                }) {
                }
            }
        }
    }
}