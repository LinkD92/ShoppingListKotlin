package com.symbol.shoppinglist.viewcomponents

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.symbol.shoppinglist.ui.productDisplay.DisplayProductViewModel

@Composable
fun FloatingActionButtonComponent(onClick: () -> Unit){
    FloatingActionButton(onClick = onClick) {
        Icon(Icons.Rounded.Add, contentDescription = "Add")
    }
}

@Composable
fun TopBarComponent(viewModel: ViewModel){
    when(viewModel){
        is DisplayProductViewModel -> {

        }
    }

}