package com.symbol.shoppinglist.core.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.symbol.shoppinglist.core.data.util.Action
import com.symbol.shoppinglist.core.presentation.navigation.CategoriesDirections
import com.symbol.shoppinglist.core.presentation.navigation.ProductsDirections

@Composable
fun AppFab(navController: NavHostController) {
    val currentScreen = navController.currentBackStackEntryAsState().value?.destination?.route
    val rootProducts = ProductsDirections.Root.route
    val rootCategories = CategoriesDirections.Root.route

    if (currentScreen == rootProducts || currentScreen == rootCategories)
        FloatingActionButton(
            modifier = Modifier,
            elevation = FloatingActionButtonDefaults.elevation(10.dp),
            onClick = {
                when (currentScreen) {
                    rootProducts -> {
                        navController.navigate(ProductsDirections.AddProduct.route)
                    }
                    rootCategories -> {
                        navController.navigate(CategoriesDirections.AddCategory.route)
                    }
                }
            }) {
            Icon(Icons.Rounded.Add, Action.ADD)
        }
}

@Composable
fun CustomButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String? = null,
    icon: ImageVector? = null,
    enabled: Boolean = true
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (icon != null) {
                Icon(icon, text)
            }
            if (icon != null && text != null) {
                Spacer(modifier = Modifier.width(5.dp))
            }
            if (text != null) {
                Text(text = text)
            }
        }
    }
}

@Composable
fun RadioButtonWithDescription(
    isSelected: Boolean,
    onClick: () -> Unit,
    description: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val fontAlpha = if (enabled) Component.Alpha.ENABLED else Component.Alpha.DISABLED
    val defaultFontColor =
        TextFieldDefaults.textFieldColors().textColor(enabled = true).value.copy(fontAlpha)
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        RadioButton(selected = isSelected, onClick = onClick, enabled = enabled)
        Text(
            text = description, Modifier.padding(horizontal = 10.dp),
            color = defaultFontColor
        )
    }
}