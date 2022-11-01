package com.symbol.shoppinglist.feature_settings.presentation.display_products

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.symbol.shoppinglist.core.data.util.Action

@Composable
fun SettingsDisplayProducts(
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState,
){
//    Spacer(modifier = Modifier.height(3.dp))
//    Row(
//        modifier
//            .fillMaxWidth()
//            .padding(horizontal = 10.dp, vertical = 5.dp)
//            .clickable { onClick() },
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Column() {
//            Row(
//                horizontalArrangement = Arrangement.Start,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Icon(icon, title)
//                Spacer(modifier = Modifier.width(3.dp))
//                Text(text = title)
//            }
//            if (secondaryText != null) {
//                Spacer(modifier = Modifier.height(1.dp))
//                Text(text = secondaryText)
//            }
//        }
//        Icon(Icons.Rounded.NavigateNext, Action.NEXT)
//    }
//    Spacer(modifier = Modifier.height(3.dp))

}