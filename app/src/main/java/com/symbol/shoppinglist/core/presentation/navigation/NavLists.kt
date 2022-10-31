package com.symbol.shoppinglist.core.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.Checklist
import androidx.compose.material.icons.rounded.ShoppingBasket
import androidx.compose.ui.graphics.vector.ImageVector
import com.symbol.shoppinglist.R


val listOfRootRoutes = listOf(
    ProductsDirections.Root.route,
    CategoriesDirections.Root.route,
    SettingsDirections.Root.route
)

val listOfRoutes = listOf(
    ProductsDirections.Root.route,
    ProductsDirections.AddProduct.route,
    CategoriesDirections.Root.route,
    CategoriesDirections.AddCategory.route,
    CategoriesDirections.ColorPicker.route
)

data class SettingsItem(val title: Int, val icon: ImageVector, val navDirection: String)
val listOfSettingsOptions = listOf(
    SettingsItem(
        R.string.display_products,
        Icons.Rounded.Checklist,
        SettingsDirections.DisplayProducts.route
    ),
    SettingsItem(
        R.string.categories,
        Icons.Rounded.Category,
        SettingsDirections.Categories.route
    ),
    SettingsItem(
        R.string.products,
        Icons.Rounded.ShoppingBasket,
        SettingsDirections.Products.route
    ),
)
