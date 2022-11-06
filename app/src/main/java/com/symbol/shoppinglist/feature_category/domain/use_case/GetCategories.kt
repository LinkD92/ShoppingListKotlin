package com.symbol.shoppinglist.feature_category.domain.use_case

import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.repository.CategoriesRepository
import com.symbol.shoppinglist.feature_category.domain.util.CategoryOrderType
import com.symbol.shoppinglist.feature_category.domain.util.FullCategoryOrderType
import com.symbol.shoppinglist.feature_category.domain.util.SortType
import com.symbol.shoppinglist.feature_settings.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCategories(
    private val categoriesRepository: CategoriesRepository,
    private val preferencesRepository: PreferencesRepository
) {
    operator fun invoke(fullCategoryOrderType: FullCategoryOrderType): Flow<List<Category>> {
        return categoriesRepository.getAllCategories().map { categories ->
            when (fullCategoryOrderType.sortType) {
                SortType.ASCENDING -> {
                    when (fullCategoryOrderType.categoryOrderType) {
                        CategoryOrderType.NAME -> categories.sortedBy { it.name }
                    }
                }
                SortType.DESCENDING -> {
                    when (fullCategoryOrderType.categoryOrderType) {
                        CategoryOrderType.NAME -> categories.sortedByDescending { it.name }
                    }
                }
                SortType.CUSTOM -> {
                    categories.sortedBy { it.customOrder }
                }
            }
        }
    }
}
