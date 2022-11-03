package com.symbol.shoppinglist.feature_category.domain.use_case

import android.util.Log
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.repository.CategoriesRepository
import com.symbol.shoppinglist.feature_category.domain.util.CategoryOrderType
import com.symbol.shoppinglist.feature_settings.domain.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCategories(
    private val categoriesRepository: CategoriesRepository,
    private val preferencesRepository: PreferencesRepository
) {

    operator fun invoke(categoryOrderType: CategoryOrderType): Flow<List<Category>> {
        return categoriesRepository.getAllCategories().map { categories ->
            when(categoryOrderType){
                CategoryOrderType.NAME -> categories.sortedBy { it.name }
                CategoryOrderType.CUSTOM -> categories.sortedBy { it.customOrder }
            }
        }
    }
}
