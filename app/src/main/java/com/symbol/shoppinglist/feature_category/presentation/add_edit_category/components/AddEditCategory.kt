package com.symbol.shoppinglist.feature_category.presentation.add_edit_category.components

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.symbol.shoppinglist.IconName
import com.symbol.shoppinglist.R
import com.symbol.shoppinglist.core.presentation.navigation.CategoriesDirections
import com.symbol.shoppinglist.feature_category.presentation.add_edit_category.AddEditCategoryEvent
import com.symbol.shoppinglist.feature_category.presentation.add_edit_category.AddEditCategoryViewModel
import com.symbol.shoppinglist.ui.ConfirmButton
import com.symbol.shoppinglist.ui.LabelAndPlaceHolder
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddCategory(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    viewModel: AddEditCategoryViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val nameState = viewModel.categoryName.value
    val colorState = viewModel.categoryColor.value
    val currentColor = Color(colorState)
    val nameLabel = stringResource(id = R.string.category_label_name)
    val snackScope = rememberCoroutineScope()
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { error ->
            snackScope.launch {
                snackbarHostState.showSnackbar(
                    message = context.getString(error.resourceString)
                )
            }
        }
    }
    Column {
        LabelAndPlaceHolder(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 5.dp),
            nameState,
            nameLabel,
        ) {
            viewModel.onEvent(AddEditCategoryEvent.EnteredName(it))
        }
        ColorPickerButton(
            buttonColor = currentColor,
            onClick = { navController.navigate(CategoriesDirections.ColorPicker.route) })
        ConfirmButton(
            onClick = {
                viewModel.onEvent(AddEditCategoryEvent.SaveCategory)
            })
    }
}

@Composable
fun ColorPickerButton(modifier: Modifier = Modifier, buttonColor: Color, onClick: () -> Unit) {
    Button(
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(buttonColor)
    ) {
        Icon(Icons.Rounded.Palette, IconName.PALETTE)
        Text(text = stringResource(id = R.string.color_select))
    }
}