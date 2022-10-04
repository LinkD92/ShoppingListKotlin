package com.symbol.shoppinglist.addProduct

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.symbol.shoppinglist.ScreenName
import com.symbol.shoppinglist.ui.theme.ShoppingListTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddProductView(navController: NavController, viewModel: AddProductViewModel = hiltViewModel()){
    Column {
        LabelAndPlaceHolder()
    }
}