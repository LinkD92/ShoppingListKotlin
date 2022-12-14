package com.symbol.shoppinglist.feature_category.domain.use_case

import com.symbol.shoppinglist.core.data.util.FieldValidation
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.model.CategoryPromptMessage
import com.symbol.shoppinglist.feature_category.domain.repository.CategoriesRepository

class InsertCategory(private val repository: CategoriesRepository) {

    suspend operator fun invoke(
        category: Category,
        validationName: String = category.name
    ): CategoryPromptMessage {
        val isNameValid = validationName == category.name
        val doesCategoryExists = repository.isCategoryNameTaken(category.name)
        if (category.name.length < FieldValidation.MIN_NAME_LENGTH
            || category.name.length > FieldValidation.MAX_NAME_LENGTH
        ) {
            return CategoryPromptMessage.InvalidLength
        }
        if (category.color == FieldValidation.DEFAULT_COLOR.toLong()) {
            return CategoryPromptMessage.InvalidColor
        }
        if (doesCategoryExists && !isNameValid) {
            return CategoryPromptMessage.ExistingName
        }
        repository.insertCategory(category)
        return CategoryPromptMessage.Success
    }
}