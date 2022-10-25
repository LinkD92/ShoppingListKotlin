package com.symbol.shoppinglist.feature_category.domain.use_case

import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.model.CategoryPromptMessage
import com.symbol.shoppinglist.feature_category.domain.repository.CategoriesRepository

class DeleteCategory(private val repository: CategoriesRepository) {

    suspend operator fun invoke(category: Category): CategoryPromptMessage{
        repository.deleteCategory(category)
        return CategoryPromptMessage.CategoryDeleted
    }
}