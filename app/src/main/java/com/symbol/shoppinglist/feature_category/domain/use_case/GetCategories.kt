package com.symbol.shoppinglist.feature_category.domain.use_case

import android.util.Log
import com.symbol.shoppinglist.DispatcherProvider
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.repository.CategoriesRepository
import com.symbol.shoppinglist.feature_category.domain.util.CategoryOrder
import com.symbol.shoppinglist.feature_settings.domain.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCategories(
    private val categoriesRepository: CategoriesRepository,
    private val preferencesRepository: PreferencesRepository
) {

    operator fun invoke(categoryOrder: String): Flow<List<Category>> {
        return categoriesRepository.getAllCategories().map { categories ->
            Log.d("QWAS - invoke:", "Cat ord: $categoryOrder")
            when(categoryOrder){
                CategoryOrder.NAME.stringValue -> categories.sortedBy { it.name }
                CategoryOrder.CUSTOM.stringValue -> categories.sortedByDescending { it.name }
                else -> categories.sortedBy { it.id }
            }
        }
    }
}
