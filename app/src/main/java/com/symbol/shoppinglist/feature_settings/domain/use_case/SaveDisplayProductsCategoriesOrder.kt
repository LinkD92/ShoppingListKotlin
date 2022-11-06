package com.symbol.shoppinglist.feature_settings.domain.use_case

import com.symbol.shoppinglist.feature_category.domain.repository.CategoriesRepository
import com.symbol.shoppinglist.feature_category.domain.util.FullCategoryOrderType
import com.symbol.shoppinglist.feature_product.domain.repository.ProductsRepository
import com.symbol.shoppinglist.feature_settings.domain.repository.PreferencesRepository

class SaveDisplayProductsCategoriesOrder(
    private val productsRepository: ProductsRepository,
    private val categoriesRepository: CategoriesRepository,
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(fullCategoryOrderType: FullCategoryOrderType) {
        preferencesRepository.saveDisplayProductsCategoryOrder(fullCategoryOrderType)
    }
}