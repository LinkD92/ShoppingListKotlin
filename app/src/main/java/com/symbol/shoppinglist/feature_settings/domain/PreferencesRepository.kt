package com.symbol.shoppinglist.feature_settings.domain

import com.symbol.shoppinglist.feature_category.domain.util.FullCategoryOrderType
import com.symbol.shoppinglist.feature_settings.domain.model.AppSettings
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {

    suspend fun saveDisplayProductsCategoryOrder(fullCategoryOrderType: FullCategoryOrderType)

    suspend fun getSettings(): Flow<AppSettings>
}