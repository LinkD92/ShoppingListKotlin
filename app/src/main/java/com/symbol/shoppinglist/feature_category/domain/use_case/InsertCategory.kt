package com.symbol.shoppinglist.feature_category.domain.use_case

import android.util.Log
import com.symbol.shoppinglist.FieldValidation
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.model.CategoryValidationError
import com.symbol.shoppinglist.feature_category.domain.repository.CategoriesRepository

class InsertCategory(private val repository: CategoriesRepository) {

    suspend operator fun invoke(category: Category, validationName: String? = null): CategoryValidationError {
        val isNameValid = validationName == category.name
        val doesCategoryExists = repository.doesCategoryExists(category.name) >= 1
        if (category.name.length < FieldValidation.MIN_NAME_LENGTH
            || category.name.length > FieldValidation.MAX_NAME_LENGTH
        ) {
            return CategoryValidationError.InvalidLength
        }
        if (category.color == FieldValidation.DEFAULT_COLOR.toLong()) {
            return CategoryValidationError.InvalidColor
        }
        if(!isNameValid && doesCategoryExists){
            return CategoryValidationError.ExistingName
        }
        repository.insertCategory(category)
        return CategoryValidationError.Success
    }
}