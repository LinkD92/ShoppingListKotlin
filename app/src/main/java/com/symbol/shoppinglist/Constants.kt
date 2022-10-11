package com.symbol.shoppinglist

import android.app.SearchManager.QUERY

object TopBarName {
    const val ADD_PRODUCT = "Add Product"
    const val EDIT_PRODUCT = "Edit Product"
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

object Error{
    const val LOADING = "Loading..."
    const val ERROR = "ERROR"
}



object NavigationRoutes{
    private const val OPTIONAL_ARGUMENT = "?"
    fun addArgument(argumentName: String, argument: String = "{$argumentName}"): String{
        return "$OPTIONAL_ARGUMENT${argumentName}=$argument"
    }
    object NavGraphs {
        const val PRODUCTS = "products"
        const val CATEGORIES = "categories"
    }

    object Products{
        const val ROOT = "products/"
        const val ADD_PRODUCT = ROOT+"addProduct/"
        object Arguments{
            const val PRODUCT_NAME = "productName"
        }
    }

    object Categories{
        const val ROOT = "categories/"
        const val ADD_CATEGORY = ROOT+"addCategory/"
        const val COLOR_PICKER = ADD_CATEGORY+"colorPicker/"
    }
}



