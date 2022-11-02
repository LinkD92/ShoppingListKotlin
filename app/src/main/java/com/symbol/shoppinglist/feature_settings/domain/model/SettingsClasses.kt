package com.symbol.shoppinglist.feature_settings.domain.model

data class SettingsGroup(val title: Int, val settingsItems: List<SettingsItem>)
data class SettingsItem(val title: Int, val navDirection: String)