package com.symbol.shoppinglist.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.symbol.shoppinglist.core.presentation.navigation.AppBottomNavigation
import com.symbol.shoppinglist.core.presentation.navigation.AppNavGraph
import com.symbol.shoppinglist.core.presentation.ui.theme.ShoppingListTheme
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShoppingListApp() {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val bottomBar = !bottomSheetState.isVisible


    ShoppingListTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            scaffoldState = scaffoldState,
            topBar = { AppTopBar(navController) },
            floatingActionButton = { AppFab(navController) },
            bottomBar = { if(bottomBar)AppBottomNavigation(navController) }
        )
        {
            Button(onClick = {
                coroutineScope.launch { bottomSheetState.show() }
            }) {
                Text(text = "test2")
            }


            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                AppNavGraph(
                    navController = navController,
                    snackbarHostState = scaffoldState.snackbarHostState
                )
            }
            ModalBottomSheetLayout(
                sheetContent =
                {
                    Text(text = "text1")
                    Text(text = "text2")
                    Text(text = "text3")
                    Text(text = "text4")
                    Text(text = "text5")
                    Text(text = "text6")
                },
                sheetState = bottomSheetState
            )
            {
            }
        }
    }
}

