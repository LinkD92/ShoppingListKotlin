package com.symbol.shoppinglist.feature_settings.presentation.settings

import com.symbol.shoppinglist.feature_category.domain.use_case.CategoryUseCases
import com.symbol.shoppinglist.feature_product.domain.use_case.ProductUseCases
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val productUseCases: ProductUseCases,
    private val categoryUseCases: CategoryUseCases
) {
}