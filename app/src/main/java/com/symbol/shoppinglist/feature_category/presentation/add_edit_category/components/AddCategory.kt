package com.symbol.shoppinglist.ui.categoriesAdd

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.symbol.shoppinglist.IconName
import com.symbol.shoppinglist.R
import com.symbol.shoppinglist.feature_category.presentation.add_edit_category.AddEditCategoryEvent
import com.symbol.shoppinglist.feature_category.presentation.add_edit_category.AddEditCategoryViewModel
import com.symbol.shoppinglist.navigation.CategoriesDirections
import com.symbol.shoppinglist.ui.ConfirmButton
import com.symbol.shoppinglist.ui.LabelAndPlaceHolder

@Composable
fun AddCategory(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: AddEditCategoryViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val nameState = viewModel.categoryName.value
    val colorState = viewModel.categoryColor.value
    val currentColor = Color(colorState)
    val nameLabel = stringResource(id = R.string.category_label_name)

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

    LaunchedEffect(true) {
//        viewModel.successObserver.collect {
//            Toast.makeText(context, context.getText(it), Toast.LENGTH_SHORT).show()
//        }
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