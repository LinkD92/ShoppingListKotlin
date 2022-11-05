package com.symbol.shoppinglist.feature_category.domain.use_case

data class CategoryUseCases(
    val getCategories: GetCategories,
    val deleteCategory: DeleteCategory,
    val insertCategory: InsertCategory,
    val getCategory: GetCategory,
    val reorderCategories: ReorderCategories,
    val expandCategory: ExpandCategory
) {
}