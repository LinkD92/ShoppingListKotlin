package com.symbol.shoppinglist.feature_category.domain.model

import com.symbol.shoppinglist.R

sealed class CategoryPromptMessage(val resourceString: Int) {
    object InvalidLength: CategoryPromptMessage(R.string.name_invalid)
    object InvalidColor: CategoryPromptMessage(R.string.category_invalid_color)
    object ExistingName: CategoryPromptMessage(R.string.name_exsists)
    object CategoryDeleted: CategoryPromptMessage(R.string.category_deleted)
    object Success: CategoryPromptMessage(R.string.category_saved)

}