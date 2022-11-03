package com.symbol.shoppinglist.feature_settings.presentation.display_products

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.RadioButton
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.symbol.shoppinglist.R

@Composable
fun SettingsDisplayProductsCategoryOrder(
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState,
) {
    Column() {
        Text(text = stringResource(id = R.string.sort_type))
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButtonWithDescription(
                isSelected = true,
                onClick = { },
                description = stringResource(id = R.string.sort_type_asc)
            )
            Spacer(modifier = Modifier.width(5.dp))
            RadioButtonWithDescription(
                isSelected = true,
                onClick = { },
                description = stringResource(id = R.string.sort_type_asc)
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp)
                .height(1.dp)
                .background(color = Color.Black.copy(alpha = 0.6f))
        )
        Text(text = stringResource(id = R.string.order_type))


    }


}

@Composable
fun RadioButtonWithDescription(
    isSelected: Boolean,
    onClick: () -> Unit,
    description: String,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        RadioButton(selected = isSelected, onClick = onClick)
        Text(text = description, Modifier.padding(horizontal = 10.dp))
    }
}