package com.symbol.shoppinglist.ui.categoriesAdd

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.symbol.shoppinglist.IconName
import com.symbol.shoppinglist.R
import com.symbol.shoppinglist.navigation.CategoriesDirections
import com.symbol.shoppinglist.ui.ConfirmButton
import com.symbol.shoppinglist.ui.LabelAndPlaceHolder

@Composable
fun AddCategory(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: AddCategoryViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val currentColor = Color(viewModel.categoryColorLong)
    val nameLabel = stringResource(id = R.string.category_label_name)

    Column {
        LabelAndPlaceHolder(Modifier.fillMaxWidth(), viewModel.categoryName, nameLabel) {
            viewModel.updateName(it)
        }
        ColorPickerButton(
            modifier.background(currentColor),
            onClick = { navController.navigate(CategoriesDirections.ColorPicker.route) })
        ConfirmButton(
            onClick = { viewModel.confirmButtonClick() })
    }

    LaunchedEffect(true) {
        viewModel.successObserver.collect {
            Toast.makeText(context, context.getText(it), Toast.LENGTH_SHORT).show()
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