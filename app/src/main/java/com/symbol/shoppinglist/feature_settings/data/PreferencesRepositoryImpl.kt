package com.symbol.shoppinglist.feature_settings.data

import androidx.datastore.core.DataStore
import com.symbol.shoppinglist.feature_category.domain.util.FullCategoryOrderType
import com.symbol.shoppinglist.feature_settings.domain.model.AppSettings
import com.symbol.shoppinglist.feature_settings.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
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