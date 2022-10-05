package com.symbol.shoppinglist.product

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.symbol.shoppinglist.displayProducts.DisplayProductViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DisplayProducts (navController: NavController, viewModel: DisplayProductViewModel = hiltViewModel()) {
    val products = viewModel.allProducts.observeAsState()
    Column() {
        Text(text = products.value?.size.toString())
    }
}



