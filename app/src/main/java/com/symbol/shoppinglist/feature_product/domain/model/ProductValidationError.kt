package com.symbol.shoppinglist.feature_product.domain.model

import com.symbol.shoppinglist.R

sealed class ProductValidationError(resourceString: Int) {
    object InvalidLength: ProductValidationError(R.string.name_invalid)
    object InvalidCategory: ProductValidationError(R.string.category_invalid)
    object ExistingName: ProductValidationError(R.string.name_exsists)
    object Success: ProductValidationError(R.string.product_added)
}