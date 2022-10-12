package com.symbol.shoppinglist.ui.categoriesAdd

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.symbol.shoppinglist.IconName
import com.symbol.shoppinglist.navigation.CategoriesDirections

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun ColorPicker(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
) {
    val backStackEntry = remember {
        navHostController.getBackStackEntry(CategoriesDirections.AddCategory.route)
    }
    val viewModel: AddCategoryViewModel = viewModel(backStackEntry)
    val controller = rememberColorPickerController()
    Surface(
        color = Color.Black.copy(alpha = 0.5f)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(all = 30.dp)
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    modifier = modifier.padding(10.dp),
                    onClick = {
                        navHostController.popBackStack()
                    }) {
                    Icon(Icons.Rounded.Check, IconName.CHECK)
                }
                AlphaTile(
                    modifier = modifier
                        .height(60.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    controller = controller
                )

            }
            HsvColorPicker(
                modifier = modifier
                    .height(200.dp)
                    .padding(10.dp),
                controller = controller,
                onColorChanged = {
                    viewModel.updateColor(it.hexCode)
                    viewModel.updateColor(it.color)
                }
            )
        }
    }
}
