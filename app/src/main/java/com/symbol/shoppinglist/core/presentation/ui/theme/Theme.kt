package com.symbol.shoppinglist.core.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.symbol.shoppinglist.core.presentation.ui.theme.MyColor.Background
import com.symbol.shoppinglist.core.presentation.ui.theme.MyColor.OnBackground
import com.symbol.shoppinglist.core.presentation.ui.theme.MyColor.OnPrimary
import com.symbol.shoppinglist.core.presentation.ui.theme.MyColor.OnSecondary
import com.symbol.shoppinglist.core.presentation.ui.theme.MyColor.OnSurface
import com.symbol.shoppinglist.core.presentation.ui.theme.MyColor.Primary
import com.symbol.shoppinglist.core.presentation.ui.theme.MyColor.Purple700
import com.symbol.shoppinglist.core.presentation.ui.theme.MyColor.Secondary
import com.symbol.shoppinglist.core.presentation.ui.theme.MyColor.Surface
import com.symbol.shoppinglist.core.presentation.ui.theme.MyColor.Teal200

private val DarkColorPalette = darkColors(
    primary = Primary,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Primary,
    onPrimary = OnPrimary,
    secondary = Secondary,
    onSecondary = OnSecondary,
    background = Background,
    onBackground = OnBackground,
    surface = Surface,
    onSurface = OnSurface,
    primaryVariant = Purple700
)

@Composable
fun ShoppingListTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = MyTypography,
        shapes = Shapes,
        content = content
    )
}