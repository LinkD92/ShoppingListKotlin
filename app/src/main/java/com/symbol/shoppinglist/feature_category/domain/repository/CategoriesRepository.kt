package com.symbol.shoppinglist.feature_category.domain.repository

import com.symbol.shoppinglist.feature_category.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {
    fun getAllCategories(): Flow<List<Category>>

    suspend fun getCategory(id: Int): Category

    suspend fun doesCategoryExists(name: String): Int

    suspend fun insertCategory(category: Category)

    suspend fun deleteCategory(category: Category)
}