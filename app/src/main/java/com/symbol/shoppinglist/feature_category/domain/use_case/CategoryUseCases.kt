package com.symbol.shoppinglist.feature_category.domain.use_case

data class CategoryUseCases(
    val getCategories: GetCategories,
    val deleteCategory: DeleteCategory,
    val addCategory: AddCategory
) {
}