package com.symbol.shoppinglist.core.presentation.components

import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@Composable
fun SnackbarNoScaffold(modifier: Modifier = Modifier, message: String){
    val snackState = remember { SnackbarHostState() }
    val snackScope = rememberCoroutineScope()

    SnackbarHost(hostState = snackState, Modifier)

    fun launchSnackBar() {
        snackScope.launch { snackState.showSnackbar(message) }
    }
}