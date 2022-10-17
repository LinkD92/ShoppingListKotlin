package com.symbol.shoppinglist

import android.app.SearchManager.QUERY

object Action {
    const val CHECK = "Check"
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

object Limits{
    const val MAX_NAME_LENGTH = 20
    const val MIN_NAME_LENGTH = 2
}



object NavigationRoutes{
    private const val OPTIONAL_ARGUMENT = "?"
    fun addArgumentName(argumentName: String): String{
        return "$OPTIONAL_ARGUMENT${argumentName}={${argumentName}}"
    }
    fun addArgument(argumentName: String, argument: Int = -1): String{
        return "$OPTIONAL_ARGUMENT${argumentName}=${argument}"
    }
    object NavGraphs {
        const val PRODUCTS = "products"
        const val CATEGORIES = "categories"
    }

    object Products{
        const val ROOT = "products/"
        const val ADD_PRODUCT = ROOT+"addProduct/"
        object Arguments{
            const val ID = "productId"
            const val NAME = "productName"
        }
    }

    object Categories{
        const val ROOT = "categories/"
        const val ADD_CATEGORY = ROOT+"addCategory/"
        const val COLOR_PICKER = ADD_CATEGORY+"colorPicker/"
        object Arguments{
            const val ID = "categoryId"
            const val NAME = "categoryName"
        }
    }

    object Arguments{
        const val INVALID_ID = -1
    }
}



