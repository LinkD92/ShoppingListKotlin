package com.symbol.shoppinglist.feature_category.domain.model

import com.symbol.shoppinglist.R

sealed class ValidationError(resourceString: Int) {
    object InvalidLength: ValidationError(R.string.name_invalid)
    object InvalidColor: ValidationError(R.string.category_invalid_color)
    object ExistingName: ValidationError(R.string.name_exsists)
    object Success: ValidationError(R.string.category_added)

}