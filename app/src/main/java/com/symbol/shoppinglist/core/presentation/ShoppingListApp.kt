package com.symbol.shoppinglist.core.presentation.components

import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.symbol.shoppinglist.core.presentation.navigation.AppBottomNavigation
import com.symbol.shoppinglist.core.presentation.navigation.AppNavGraph
import com.symbol.shoppinglist.core.presentation.ui.theme.ShoppingListTheme


@Composable
fun ShoppingListApp() {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ){ isGranted ->
        if (isGranted)
            Log.d("QWAS:", "26:ShoppingListApp -> $isGranted")
        else
            Log.d("QWAS:", "29:ShoppingListApp -> $isGranted")
    }
    
    val test = PackageManager.PERMISSION_GRANTED
    Log.d("QWAS:", "34:ShoppingListApp -> $test")


    ShoppingListTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            scaffoldState = scaffoldState,
            topBar = { AppTopBar(navController) },
            floatingActionButton = { AppFab(navController) },
            bottomBar = { AppBottomNavigation(navController) }
        )
        {
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
        }
    }
}

