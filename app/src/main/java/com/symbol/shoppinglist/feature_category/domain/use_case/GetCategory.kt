package com.symbol.shoppinglist.feature_category.domain.use_case

import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.repository.CategoriesRepository

class GetCategory(private val repository: CategoriesRepository) {

    suspend operator fun invoke(id: Int): Category{
        return repository.getCategory(id)
    }
}