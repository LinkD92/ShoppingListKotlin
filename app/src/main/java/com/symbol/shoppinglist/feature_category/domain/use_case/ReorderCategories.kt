package com.symbol.shoppinglist.feature_product.domain.use_case

import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.repository.CategoriesRepository

class ReorderCategories(private val repository: CategoriesRepository) {
    suspend operator fun invoke(categories: List<Category>){
        repository.insertCategories(categories)
    }
}