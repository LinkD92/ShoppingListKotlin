package com.symbol.shoppinglist.feature_category.domain.use_case

import com.symbol.shoppinglist.FieldValidation
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.model.CategoryValidationError
import com.symbol.shoppinglist.feature_category.domain.repository.CategoriesRepository

class AddCategory(private val repository: CategoriesRepository) {

    suspend operator fun invoke(category: Category): CategoryValidationError {
        if (category.name.length < FieldValidation.MIN_NAME_LENGTH
            || category.name.length > FieldValidation.MAX_NAME_LENGTH
        ) {
            return CategoryValidationError.InvalidLength
        }
        if (category.color == FieldValidation.DEFAULT_COLOR.toLong()) {
            return CategoryValidationError.InvalidColor
        }
        repository.insertCategory(category)
        return CategoryValidationError.Success
    }
}