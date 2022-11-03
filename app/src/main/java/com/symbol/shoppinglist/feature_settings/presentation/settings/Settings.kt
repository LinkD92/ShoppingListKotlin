package com.symbol.shoppinglist.feature_settings.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.NavigateNext
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.symbol.shoppinglist.core.data.util.Action
import com.symbol.shoppinglist.core.presentation.navigation.listOfSettingsOptions
import com.symbol.shoppinglist.core.presentation.ui.theme.MyColor

@Composable
fun Settings(
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    Column() {
        listOfSettingsOptions.forEachIndexed { index, settingGroup ->
            if (index != 0) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(3.dp)
                        .height(1.dp)
                        .background(color = Color.Black.copy(alpha = 0.6f))
                )
            }
            SettingsGroup(groupName = stringResource(id = settingGroup.title)) {
                settingGroup.settingsItems.forEach { settingItem ->
                    SettingsItem(title = stringResource(id = settingItem.title), onClick = {
                        if (index % 2 == 0)
                            viewModel.changeCategoryOrderType()
                        else
                            viewModel.changeCategoryOrderType1()
                    })
                }
            }
        }
    }

//    val shouldShowBox = remember { mutableStateOf(true) }
//    var text by remember { mutableStateOf(0) }
//    Column(verticalArrangement = Arrangement.SpaceBetween) {
//        listOfSettingsOptions.forEach { item ->
//            SettingsItem(
//                title = stringResource(id = item.title),
//                icon = item.icon,
//                onClick = { navHostController.navigate(item.navDirection) })
//        }
//        val alpha = if (shouldShowBox.value) 1f else 0.2f
//
//        Button(onClick = {
//            shouldShowBox.value = !shouldShowBox.value
//            text++
//        }) {
//            Text(text = "Click Me")
//        }
//        Box(
//            Modifier
//                .fillMaxSize()
//
//        ) {
//
//            Column(Modifier.clickable {
//                text++
//            }) {
//                Text(text = "test1$text")
//                Text(text = "test1$text")
//                Text(text = "test1$text")
//                Text(text = "test1$text")
//            }
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color.Yellow.copy(alpha))
//                    .clickable { text-- }
//            ) {
//
//            }
//        }
//    }
}


@Composable
fun SettingsGroup(
    groupName: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 5.dp),
        horizontalAlignment = Alignment.Start
    ) {

        val textFieldColor = TextFieldDefaults.textFieldColors().textColor(enabled = true).value
        Text(text = groupName, color = MyColor.OnPrimary)
        content()
    }
}

@Composable
fun SettingsItem(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    secondaryText: String? = null
) {
    Spacer(modifier = Modifier.height(3.dp))
    Row(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
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

