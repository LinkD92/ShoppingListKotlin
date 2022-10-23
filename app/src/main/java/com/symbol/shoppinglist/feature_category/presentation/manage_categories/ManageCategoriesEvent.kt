package com.symbol.shoppinglist.ui.categoriesManage

import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.util.CategoryOrder

sealed class ManageCategoriesEvent {
    data class Order(val categoryOrder: CategoryOrder): ManageCategoriesEvent()
    data class DeleteCategory(val category: Category): ManageCategoriesEvent()
    data class EditCategory(val category: Category): ManageCategoriesEvent()
    data class RestoreCategory(val category: Category): ManageCategoriesEvent()
    object CreateCategory: ManageCategoriesEvent()
}