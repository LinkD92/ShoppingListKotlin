package com.symbol.shoppinglist.core.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.symbol.shoppinglist.core.presentation.ui.theme.Shapes

@Composable
fun OptionsDialog(
    modifier: Modifier = Modifier,
    shouldOpenDialog: () -> Unit,
    title: String,
    btn1Name: String,
    btn1Icon: ImageVector? = null,
    btn1OnClick: () -> Unit,
    btn2Name: String,
    btn2Icon: ImageVector? = null,
    btn2OnClick: () -> Unit
) {
    Dialog(
        onDismissRequest = { shouldOpenDialog() },
    ) {
        Surface(
            modifier = modifier,
            shape = Shapes.medium
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(onClick = { btn1OnClick() }) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = btn1Name)
                            Spacer(modifier = Modifier.width(5.dp))
                            if (btn1Icon != null)
                                Icon(btn1Icon, btn1Name)
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(onClick = { btn2OnClick() }) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = btn2Name, textAlign = TextAlign.Center)
                            Spacer(modifier = Modifier.width(5.dp))
                            if (btn2Icon != null)
                                Icon(btn2Icon, btn2Name)
                        }
                    }
                }
            }
        }
    }
}