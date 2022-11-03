package com.symbol.shoppinglist.feature_settings.domain

import androidx.datastore.preferences.core.Preferences
import com.symbol.shoppinglist.core.domain.util.OrderType
import com.symbol.shoppinglist.feature_category.domain.util.CategoryOrder
import com.symbol.shoppinglist.feature_category.domain.util.CategoryOrderEnum

interface PreferencesRepository {

    suspend fun saveDisplayProductsCategoryOrder(value: CategoryOrderEnum)
}