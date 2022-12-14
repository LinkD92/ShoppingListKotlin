package com.symbol.shoppinglist.feature_product.domain.use_case

import com.symbol.shoppinglist.core.data.util.FieldValidation
import com.symbol.shoppinglist.core.data.util.NavigationRoutes
import com.symbol.shoppinglist.feature_product.domain.model.Product
import com.symbol.shoppinglist.feature_product.domain.model.ProductPromptMessage
import com.symbol.shoppinglist.feature_product.domain.repository.ProductsRepository

class InsertProduct(private val repository: ProductsRepository) {
    private val invalidId = NavigationRoutes.Arguments.INVALID_ID

    suspend operator fun invoke(
        product: Product,
        validationName: String = product.name
    ): ProductPromptMessage {
        val isNameValid = validationName == product.name
        val isProductNameTaken = repository.isProductNameTaken(product.name)
        if (product.name.length < FieldValidation.MIN_NAME_LENGTH
            || product.name.length > FieldValidation.MAX_NAME_LENGTH
        ) {
            return ProductPromptMessage.InvalidLength
        }
        if (product.categoryId == invalidId) {
            return ProductPromptMessage.InvalidCategory
        }
        if (!isNameValid && isProductNameTaken) {
            return ProductPromptMessage.ExistingName
        }
        repository.insertProduct(product)
        return ProductPromptMessage.Success
    }
}