package com.symbol.shoppinglist.feature_product.domain.model

import com.symbol.shoppinglist.R

sealed class ProductPromptMessage(val resourceString: Int) {
    object InvalidLength: ProductPromptMessage(R.string.name_invalid)
    object InvalidCategory: ProductPromptMessage(R.string.category_invalid)
    object ExistingName: ProductPromptMessage(R.string.name_exsists)
    object ProductDeleted: ProductPromptMessage(R.string.product_deleted)
    object Success: ProductPromptMessage(R.string.product_added)
}