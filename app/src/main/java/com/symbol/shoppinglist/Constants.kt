package com.symbol.shoppinglist

import com.symbol.shoppinglist.ui.navigation.BottomNavigationDirection

object ScreenName {
    const val ADD_PRODUCT = "Add Product"
    const val PRODUCTS = "Products"
    const val CATEGORIES = "Categories"
    const val ADD_CATEGORY = "Add Category"
    const val COLOR_PICKER = "Color Picker"
}

object Action {
    const val BACK = "Back"
    const val ADD = "Add"
    const val HOME = "Home"
}

object IconName {
    const val EXPAND = "Expand"
    const val PALETTE = "Palette"
    const val CHECK = "Check"
    const val DELETE = "Delete"
    const val DROPDOWN = "Dropdown"
}



object NavigationRoutes{
    object NavGraphs {
        private const val NAV_GRAPH = ""
        const val ROOT = "root$NAV_GRAPH"
        const val PRODUCTS = "products$NAV_GRAPH"
        const val CATEGORIES = "categories$NAV_GRAPH"
    }

    object BottomNavigationRoutes{
        const val PRODUCTS = "products"
        const val CATEGORIES = "categories"
    }

    object Products{
        const val ROOT = "products/"
        const val ADD_PRODUCT = ROOT+"addProduct/"
    }

    object Categories{
        const val ROOT = "categories/"
        const val ADD_CATEGORY = ROOT+"addCategory/"
        const val COLOR_PICKER = ADD_CATEGORY+"colorPicker/"
    }
}