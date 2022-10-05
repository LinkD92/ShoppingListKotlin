package com.symbol.shoppinglist.addProduct

import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import com.symbol.shoppinglist.Action

@Composable
fun LabelAndPlaceHolder(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
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

@Composable
fun AddButton(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Icon(Icons.Rounded.Add, Action.ADD)
    }
}