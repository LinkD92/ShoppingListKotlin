package com.symbol.shoppinglist.product

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.symbol.shoppinglist.ui.productDisplay.DisplayProductViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DisplayProducts(
    modifier: Modifier = Modifier,
    viewModel: DisplayProductViewModel = hiltViewModel()
) {
    val products = viewModel.allProducts.observeAsState()
    Column() {
        Text(text = products.value?.size.toString())
    }
}



