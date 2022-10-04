package com.symbol.shoppinglist.addProduct

import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun LabelAndPlaceHolder() {
    var text by rememberSaveable { mutableStateOf(TextFieldValue("")) }
    TextField(
        value = text,
        onValueChange = {
        },
        label = { Text(text = "Your Label") },
        placeholder = { Text(text = "Your Placeholder/Hint") },
    )
}

@Composable
fun SimpleTextField() {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    TextField(
        value = text,
        onValueChange = { newText ->
            text = newText
        }
    )
}