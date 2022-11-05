package com.symbol.shoppinglist.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.symbol.shoppinglist.core.presentation.ui.theme.MyColor
import com.symbol.shoppinglist.core.presentation.ui.theme.Typography


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

@Composable
fun CustomViewHeader(
    headerTitle: String,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = 10.dp,
        modifier = modifier,
        backgroundColor = MyColor.Primary
    ) {
        Column(
            modifier = modifier.padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = headerTitle, color = MyColor.OnPrimary, style = Typography.h2)
        }
    }
}