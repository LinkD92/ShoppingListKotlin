package com.symbol.shoppinglist.feature_category.domain.use_case

import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.repository.CategoriesRepository

class ExpandCategory(private val repository: CategoriesRepository) {

    suspend operator fun invoke(category: Category){
        repository.insertCategory(category)
    }
}