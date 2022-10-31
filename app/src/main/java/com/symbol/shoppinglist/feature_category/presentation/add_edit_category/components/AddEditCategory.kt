package com.symbol.shoppinglist.feature_category.presentation.add_edit_category.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.symbol.shoppinglist.R
import com.symbol.shoppinglist.core.data.util.IconName
import com.symbol.shoppinglist.core.presentation.components.ConfirmButton
import com.symbol.shoppinglist.core.presentation.components.LabelAndPlaceHolder
import com.symbol.shoppinglist.core.presentation.navigation.CategoriesDirections
import com.symbol.shoppinglist.feature_category.presentation.add_edit_category.AddEditCategoryEvent
import com.symbol.shoppinglist.feature_category.presentation.add_edit_category.AddEditCategoryViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditCategory(
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState,
    viewModel: AddEditCategoryViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val nameState = viewModel.categoryName.value
    val colorState = viewModel.categoryColor.value
    val currentColor = Color(colorState)
    val snackScope = rememberCoroutineScope()
    val nameLabel = stringResource(id = R.string.category_label_name)

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { error ->
            snackScope.launch {
                snackbarHostState.showSnackbar(
                    message = context.getString(error.resourceString)
                )
            }
        }
    }
    Column(modifier = Modifier.fillMaxWidth()) {
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
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .fillMaxWidth(),
            buttonColor = currentColor,
            onClick = { navHostController.navigate(CategoriesDirections.ColorPicker.route) })
        ConfirmButton(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .align(Alignment.End),
            onClick = { viewModel.onEvent(AddEditCategoryEvent.SaveCategory) }
        )
    }
}

@Composable
fun ColorPickerButton(modifier: Modifier = Modifier, buttonColor: Color, onClick: () -> Unit) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(buttonColor)
    ) {
        Icon(Icons.Rounded.Palette, IconName.PALETTE)
        Text(text = stringResource(id = R.string.color_select))
    }
}