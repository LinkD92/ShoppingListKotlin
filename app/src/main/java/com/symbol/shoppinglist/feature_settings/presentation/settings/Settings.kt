package com.symbol.shoppinglist.feature_settings.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.NavigateNext
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.symbol.shoppinglist.R
import com.symbol.shoppinglist.core.data.util.Action
import com.symbol.shoppinglist.core.presentation.navigation.SettingsDirections
import com.symbol.shoppinglist.core.presentation.ui.theme.MyColor
import java.util.*

@Composable
fun Settings(
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state = viewModel.stateSettingsDisplayProduct
    Column() {
        SettingsGroup(groupName = stringResource(id = R.string.display_products)) {
            SettingsItem(
                title = stringResource(id = R.string.category_reorder), onClick = {
                    navHostController.navigate(
                        SettingsDirections.DisplayProductsCategoryOrder.route
                    )
                }
            )
        }
        SettingsGroup(groupName = stringResource(id = R.string.categories)) {
            SettingsItem(title = stringResource(id = R.string.category_reorder),
                onClick = { navHostController.navigate(SettingsDirections.Categories.route) })
        }
        SettingsGroup(groupName = stringResource(id = R.string.products)) {
            SettingsItem(title = stringResource(id = R.string.category_reorder),
                onClick = { navHostController.navigate(SettingsDirections.Products.route) })
        }
    }
}


@Composable
fun SettingsGroup(
    groupName: String, modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier, horizontalAlignment = Alignment.Start) {
        Text(text = groupName, color = MyColor.OnPrimary)
        content()
    }
}

@Composable
fun SettingsItem(
    title: String, onClick: () -> Unit, modifier: Modifier = Modifier, secondaryText: String? = null
) {
    Spacer(modifier = Modifier.height(3.dp))
    Row(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .clickable { onClick() }, horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column() {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = title)
            }
            if (secondaryText != null) {
                Spacer(modifier = Modifier.height(1.dp))
                Text(text = secondaryText)
            }
        }
        Icon(Icons.Rounded.NavigateNext, Action.NEXT)
    }
    Spacer(modifier = Modifier.height(3.dp))
}

fun String.capitalized(): String {
    return this.lowercase().replaceFirstChar {
        if (it.isLowerCase())
            it.titlecase(Locale.getDefault())
        else it.toString()
    }
}