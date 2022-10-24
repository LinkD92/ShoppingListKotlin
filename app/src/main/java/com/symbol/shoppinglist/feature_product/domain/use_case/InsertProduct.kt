package com.symbol.shoppinglist.feature_product.domain.use_case

import com.symbol.shoppinglist.FieldValidation
import com.symbol.shoppinglist.NavigationRoutes
import com.symbol.shoppinglist.feature_category.domain.model.CategoryValidationError
import com.symbol.shoppinglist.feature_product.domain.model.Product
import com.symbol.shoppinglist.feature_product.domain.model.ProductValidationError
import com.symbol.shoppinglist.feature_product.domain.repository.ProductsRepository

class AddProduct(private val repository: ProductsRepository) {
    private val invalidId = NavigationRoutes.Arguments.INVALID_ID

    suspend operator fun invoke(product: Product): ProductValidationError {
        if (product.name.length < FieldValidation.MIN_NAME_LENGTH
            || product.name.length > FieldValidation.MAX_NAME_LENGTH
        ) {
            return ProductValidationError.InvalidLength
        }
        if (product.categoryId == invalidId) {
            return ProductValidationError.InvalidCategory
        }
        repository.insertProduct(product)
        return ProductValidationError.Success
    }
}