package com.symbol.shoppinglist.feature_category.domain.model

import com.symbol.shoppinglist.R

sealed class CategoryValidationError(resourceString: Int) {
    object InvalidLength: CategoryValidationError(R.string.name_invalid)
    object InvalidColor: CategoryValidationError(R.string.category_invalid_color)
    object ExistingName: CategoryValidationError(R.string.name_exsists)
    object Success: CategoryValidationError(R.string.category_added)

}