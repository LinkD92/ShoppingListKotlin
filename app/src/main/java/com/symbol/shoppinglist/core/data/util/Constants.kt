package com.symbol.shoppinglist.core.data.util

object Database {
    const val NAME = "products_database"
}

object PreferencesDataStore{
    const val FILE_NAME = "app-settings.json"
    const val NAME = "user_preferences"


    object Key{
        const val DISPLAY_PRODUCT_CATEGORY_ORDER = "display_product_category_order"
    }
}

object Action {
    const val CHECK = "Check"
    const val BACK = "Back"
    const val ADD = "Add"
    const val HOME = "Home"
    const val NEXT = "Next"
}

object IconName {
    const val EXPAND = "Expand"
    const val PALETTE = "Palette"
    const val CHECK = "Check"
    const val DELETE = "Delete"
    const val DROPDOWN = "Dropdown"
}

object Error {
    const val LOADING = "Loading..."
    const val ERROR = "ERROR"
    const val IN_PROGRESS = "In progress..."
}

object FieldValidation {
    const val MAX_NAME_LENGTH = 20
    const val MIN_NAME_LENGTH = 3
    const val DEFAULT_COLOR = 0
}


object NavigationRoutes {
    private const val OPTIONAL_ARGUMENT = "?"
    fun addArgumentName(argumentName: String): String {
        return "$OPTIONAL_ARGUMENT${argumentName}={${argumentName}}"
    }

    fun addArgument(argumentName: String, argument: Int = -1): String {
        return "$OPTIONAL_ARGUMENT${argumentName}=${argument}"
    }

    object NavGraphs {
        const val PRODUCTS = "products"
        const val CATEGORIES = "categories"
        const val SETTINGS = "settings"
    }

    object Products {
        const val ROOT = "products/"
        const val ADD_PRODUCT = ROOT + "addProduct/"

        object Arguments {
            const val ID = "productId"
            const val NAME = "productName"
        }
    }

    object Categories {
        const val ROOT = "categories/"
        const val ADD_CATEGORY = ROOT + "addCategory/"
        const val COLOR_PICKER = ADD_CATEGORY + "colorPicker/"

        object Arguments {
            const val ID = "categoryId"
            const val NAME = "categoryName"
        }
    }

    object Settings {
        const val ROOT = "settings/"
        const val DISPLAY_PRODUCTS_CATEGORY_ORDER = ROOT + "displayProductsCategoryOrder/"
        const val CATEGORIES = ROOT + "categories/"
        const val PRODUCTS = ROOT + "products/"
    }

    object Arguments {
        const val INVALID_ID = -1
    }
}



