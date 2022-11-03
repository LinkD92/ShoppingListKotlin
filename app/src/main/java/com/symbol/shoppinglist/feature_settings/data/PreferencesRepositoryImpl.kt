package com.symbol.shoppinglist.feature_settings.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.symbol.shoppinglist.core.data.util.PreferencesDataStore
import com.symbol.shoppinglist.feature_category.domain.util.CategoryOrder
import com.symbol.shoppinglist.feature_settings.domain.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferencesRepository {

    override suspend fun saveDisplayProductsCategoryOrder(value: CategoryOrder) {
        val key =
            stringPreferencesKey(name = PreferencesDataStore.Key.DISPLAY_PRODUCT_CATEGORY_ORDER)
            dataStore.edit { settings ->
                settings[key] = value.stringValue
            }
    }

    override fun getDisplayProductsCategoryOrder(): Flow<String> {
        return dataStore.data.map { preferences ->
            val key =
                stringPreferencesKey(name = PreferencesDataStore.Key.DISPLAY_PRODUCT_CATEGORY_ORDER)
            preferences[key] ?: CategoryOrder.NAME.stringValue
        }
    }
}