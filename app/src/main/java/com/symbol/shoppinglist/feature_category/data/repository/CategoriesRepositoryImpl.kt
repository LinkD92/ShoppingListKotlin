package com.symbol.shoppinglist.feature_category.data.repository

import com.symbol.shoppinglist.feature_category.data.data_source.CategoriesDao
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.repository.CategoriesRepository
import kotlinx.coroutines.flow.Flow

class CategoriesRepositoryImpl(private val categoriesDao: CategoriesDao): CategoriesRepository {

    override fun getAllCategories(): Flow<List<Category>> = categoriesDao.getAllCategories()

    override suspend fun getCategory(id: Int) = categoriesDao.getCategory(id)

    override suspend fun doesCategoryExists(name: String) = categoriesDao.doesCategoryExists(name)

    override suspend fun addCategory(category: Category) = categoriesDao.addCategory(category)

    override suspend fun deleteCategory(category: Category) = categoriesDao.deleteCategory(category)

    override suspend fun updateCategory(category: Category) = categoriesDao.updateCategory(category)
}