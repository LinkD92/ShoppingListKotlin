package com.symbol.shoppinglist.feature_category.domain.use_case

import com.symbol.shoppinglist.feature_product.domain.use_case.ExpandCategory
import com.symbol.shoppinglist.navigation.CategoriesDirections

data class CategoryUseCases(
    val getCategories: GetCategories,
    val deleteCategory: DeleteCategory,
    val insertCategory: InsertCategory,
    val getCategory: GetCategory,
) {
}