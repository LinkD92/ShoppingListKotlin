package com.symbol.shoppinglist

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.symbol.shoppinglist.data.Product
import com.symbol.shoppinglist.ui.theme.ShoppingListTheme

@Composable
fun ProductView(product: Product){
    ShoppingListTheme {
        Surface {
            Row(modifier = Modifier.padding(8.dp)){
                Text(text = product.category,
                    modifier = Modifier.border(1.5.dp, MaterialTheme.colors.primary,  CircleShape)
                )
                Spacer(modifier = Modifier.width(24.dp))
                Column {
                    Text(text = product.name,
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.body2
                    )
                    Surface(
                        shape = MaterialTheme.shapes.medium,
                        elevation = 6.dp
                    ) {
                        Text(text = product.price.toString(),
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
        }
    }
}}