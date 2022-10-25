package com.symbol.shoppinglist.feature_category.domain.use_case

import com.symbol.shoppinglist.FieldValidation
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.model.CategoryPromptMessage
import com.symbol.shoppinglist.feature_category.domain.repository.CategoriesRepository

class InsertCategory(private val repository: CategoriesRepository) {

    suspend operator fun invoke(category: Category, validationName: String? = null): CategoryPromptMessage {
        val isNameValid = validationName == category.name
        val doesCategoryExists = repository.doesCategoryExists(category.name) >= 1
        if (category.name.length < FieldValidation.MIN_NAME_LENGTH
            || category.name.length > FieldValidation.MAX_NAME_LENGTH
        ) {
            return CategoryPromptMessage.InvalidLength
        }
        if (category.color == FieldValidation.DEFAULT_COLOR.toLong()) {
            return CategoryPromptMessage.InvalidColor
        }
        if(!isNameValid && doesCategoryExists){
            return CategoryPromptMessage.ExistingName
        }
        repository.insertCategory(category)
        return CategoryPromptMessage.Success
    }
}