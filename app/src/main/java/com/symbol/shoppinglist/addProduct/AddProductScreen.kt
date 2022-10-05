package com.symbol.shoppinglist.addProduct

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddProductScreen(
    navController: NavController,
    viewModel: AddProductViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    Column {
        LabelAndPlaceHolder(viewModel.productName) {
            viewModel.updateName(it)
//            viewModel.onProductUiStateChange(it)
        }
        AddButton(onClick = { viewModel.addProduct() })
    }

    LaunchedEffect(true) {
        viewModel.successObserver.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }


}