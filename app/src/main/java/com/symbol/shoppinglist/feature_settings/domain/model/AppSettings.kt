package com.symbol.shoppinglist.feature_settings.domain.model

import com.symbol.shoppinglist.feature_category.domain.util.CategoryExpandStatus
import com.symbol.shoppinglist.feature_category.domain.util.CategoryOrderType
import com.symbol.shoppinglist.feature_category.domain.util.FullCategoryOrderType
import com.symbol.shoppinglist.feature_category.domain.util.SortType
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable


@Serializable
data class AppSettings(
    val categoryOrderType: CategoryOrderType = CategoryOrderType.NAME,
    val categoryExpandStatus: PersistentList<CategoryExpandStatus> = persistentListOf(),
    val fullCategoryOrderType: FullCategoryOrderType = FullCategoryOrderType(
        CategoryOrderType.NAME,
        SortType.ASCENDING
    )
)
