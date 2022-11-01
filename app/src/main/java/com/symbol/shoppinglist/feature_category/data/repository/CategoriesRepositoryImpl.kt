package com.symbol.shoppinglist.feature_category.data.repository

import com.symbol.shoppinglist.feature_category.data.data_source.CategoriesDao
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.repository.CategoriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(private val categoriesDao: CategoriesDao) :
    CategoriesRepository {

    override fun getAllCategories(): Flow<List<Category>> = categoriesDao.getAllCategories()

    override suspend fun getCategory(id: Int) = categoriesDao.getCategory(id)

    override suspend fun isCategoryNameTaken(name: String): Boolean =
        (categoriesDao.categoryNameCount(name) >= 1)


    override suspend fun insertCategory(category: Category) = categoriesDao.insertCategory(category)

    override suspend fun insertCategories(categories: List<Category>) = categoriesDao.insertCategories(categories)

    override suspend fun deleteCategory(category: Category) = categoriesDao.deleteCategory(category)
}