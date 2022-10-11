package com.symbol.shoppinglist.ui.categoriesAdd

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.symbol.shoppinglist.IconName
import com.symbol.shoppinglist.navigation.CategoriesDirections
import com.symbol.shoppinglist.ui.productAdd.AddButton
import com.symbol.shoppinglist.ui.productAdd.LabelAndPlaceHolder

@Composable
fun AddCategory(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: AddCategoryViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val test = Color(viewModel.test)

    Column {
        LabelAndPlaceHolder(viewModel.categoryName) {
            viewModel.updateCategoryName(it)
        }
        ColorPickerButton(
            modifier.background(test),
            onClick = { navController.navigate(CategoriesDirections.ColorPicker.route) })
        AddButton(
            onClick = { viewModel.addCategory() })
    }

    LaunchedEffect(true) {
        viewModel.successObserver.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun ColorPickerButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(Icons.Rounded.Palette, IconName.PALETTE)
        Text(text = "Choose Color")
    }
}