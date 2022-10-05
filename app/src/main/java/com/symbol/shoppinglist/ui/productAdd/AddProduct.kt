package com.symbol.shoppinglist.ui.productAdd

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddProduct(
    modifier: Modifier = Modifier,
    viewModel: AddProductViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    Column {
        LabelAndPlaceHolder(viewModel.productName) {
            viewModel.updateName(it)
        }
        AddButton(onClick = { viewModel.addProduct() })
    }

    LaunchedEffect(true) {
        viewModel.successObserver.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }


}