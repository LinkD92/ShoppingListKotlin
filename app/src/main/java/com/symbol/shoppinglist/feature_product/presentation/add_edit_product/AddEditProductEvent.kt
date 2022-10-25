package com.symbol.shoppinglist.feature_product.presentation.add_edit_product

import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_product.domain.model.Product

sealed class AddEditProductEvent {
    object SaveProduct: AddEditProductEvent()
    data class EnteredName(val value: String): AddEditProductEvent()
    data class EnteredQuantity(val value: String): AddEditProductEvent()
    data class ChooseCategory(val value: Category): AddEditProductEvent()
}