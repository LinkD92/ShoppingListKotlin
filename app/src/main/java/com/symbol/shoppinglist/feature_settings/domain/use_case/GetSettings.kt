package com.symbol.shoppinglist.feature_settings.domain.use_case

import com.symbol.shoppinglist.feature_settings.domain.PreferencesRepository
import com.symbol.shoppinglist.feature_settings.domain.model.AppSettings
import kotlinx.coroutines.flow.Flow

class GetSettings(private val preferencesRepository: PreferencesRepository) {

    suspend operator fun invoke(): Flow<AppSettings> {
        return preferencesRepository.getSettings()
    }
}