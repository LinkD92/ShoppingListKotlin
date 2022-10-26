package com.symbol.shoppinglist.core.presentation.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun LabelAndPlaceHolder(
    modifier: Modifier = Modifier,
    value: String,
    labelTitle: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    onValueChange: (String) -> Unit
) {
    TextField(
        modifier = modifier,
        keyboardOptions = keyboardOptions,
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = labelTitle) },
        placeholder = { Text(text = labelTitle) },
    )
}