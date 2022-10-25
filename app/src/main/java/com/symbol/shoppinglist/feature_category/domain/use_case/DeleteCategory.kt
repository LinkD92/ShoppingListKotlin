package com.symbol.shoppinglist.feature_category.domain.use_case

import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.model.CategoryPromptMessage
import com.symbol.shoppinglist.feature_category.domain.repository.CategoriesRepository
import com.symbol.shoppinglist.feature_product.domain.repository.ProductsRepository

class DeleteCategory(
    private val categoriesRepository: CategoriesRepository,
    private val productsRepository: ProductsRepository
) {

    suspend operator fun invoke(category: Category): CategoryPromptMessage {
        categoriesRepository.deleteCategory(category)
        productsRepository.deleteCategoryProducts(category.id)
        return CategoryPromptMessage.CategoryDeleted
    }
}