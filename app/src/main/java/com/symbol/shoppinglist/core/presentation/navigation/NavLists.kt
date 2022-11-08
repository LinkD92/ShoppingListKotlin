package com.symbol.shoppinglist.core.presentation.navigation

import com.symbol.shoppinglist.R
import com.symbol.shoppinglist.feature_settings.domain.model.SettingsGroup
import com.symbol.shoppinglist.feature_settings.domain.model.SettingsItem


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


val listOfSettingsOptions = listOf(
    SettingsGroup(
        R.string.display_products,
        listOf(
            SettingsItem(R.string.category_order, SettingsDirections.DisplayProductsCategoryOrder.route)
        )
    ),
    SettingsGroup(
        R.string.categories,
        listOf(
            SettingsItem(R.string.category_order, SettingsDirections.Categories.route)
        )
    ),
    SettingsGroup(
        R.string.products,
        listOf(
            SettingsItem(R.string.category_order, SettingsDirections.Products.route)
        )
    ),
)
