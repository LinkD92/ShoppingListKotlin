package com.symbol.shoppinglist.viewcomponents

import android.graphics.drawable.Icon
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.symbol.shoppinglist.displayProducts.DisplayProductViewModel

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