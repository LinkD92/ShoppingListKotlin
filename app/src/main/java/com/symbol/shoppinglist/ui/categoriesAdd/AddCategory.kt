package com.symbol.shoppinglist.ui.categoriesAdd

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.symbol.shoppinglist.IconName
import com.symbol.shoppinglist.ScreenName
import com.symbol.shoppinglist.ui.productAdd.AddButton
import com.symbol.shoppinglist.ui.productAdd.LabelAndPlaceHolder

@Composable
fun AddCategory(
    modifier: Modifier = Modifier,
    viewModel: AddCategoryViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    Column {
        LabelAndPlaceHolder(viewModel.categoryName) {
            viewModel.updateCategoryName(it)
        }
        ColorPickerButton(onClick = { navController.navigate(ScreenName.COLOR_PICKER) })
        AddButton(onClick = { viewModel.addCategory() })
    }

    LaunchedEffect(true) {
        viewModel.successObserver.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun ColorPickerButton(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Icon(Icons.Rounded.Palette, IconName.PALETTE)
        Text(text = "Choose Color")
    }
}