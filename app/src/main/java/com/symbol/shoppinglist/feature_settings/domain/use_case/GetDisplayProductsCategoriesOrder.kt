package com.symbol.shoppinglist.feature_settings.domain.use_case

import com.symbol.shoppinglist.feature_settings.domain.PreferencesRepository
import kotlinx.coroutines.flow.Flow

class GetDisplayProductsCategoriesOrder(private val preferencesRepository: PreferencesRepository) {

    operator fun invoke(): Flow<String> {
        return preferencesRepository.getDisplayProductsCategoryOrder()
    }
}