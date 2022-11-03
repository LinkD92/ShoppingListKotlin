package com.symbol.shoppinglist.feature_settings.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.symbol.shoppinglist.core.data.util.PreferencesDataStore
import com.symbol.shoppinglist.feature_category.domain.util.CategoryOrderType
import com.symbol.shoppinglist.feature_category.domain.util.FullCategoryOrderType
import com.symbol.shoppinglist.feature_settings.domain.PreferencesRepository
import com.symbol.shoppinglist.feature_settings.domain.model.AppSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<AppSettings>
) : PreferencesRepository {

    override suspend fun saveDisplayProductsCategoryOrder(fullCategoryOrderType: FullCategoryOrderType) {
        dataStore.updateData {
            it.copy(fullCategoryOrderType = fullCategoryOrderType)
        }
    }

    override suspend fun getSettings(): Flow<AppSettings> {
        return dataStore.data
    }
}