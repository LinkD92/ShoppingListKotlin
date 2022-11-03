package com.symbol.shoppinglist.feature_settings.domain

import com.symbol.shoppinglist.feature_category.domain.util.CategoryOrder
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {

    suspend fun saveDisplayProductsCategoryOrder(value: CategoryOrder)

    fun getDisplayProductsCategoryOrder(): Flow<String>
}