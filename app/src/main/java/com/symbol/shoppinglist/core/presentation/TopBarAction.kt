package com.symbol.shoppinglist.core.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.ui.graphics.vector.ImageVector
import com.symbol.shoppinglist.R

sealed class TopBarAction(val label: Int, val icon: ImageVector) {
    object ReorderCategory : TopBarAction(R.string.category_order, Icons.Rounded.MoreVert)
}