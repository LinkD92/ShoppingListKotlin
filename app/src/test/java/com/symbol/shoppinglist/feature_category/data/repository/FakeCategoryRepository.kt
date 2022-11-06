package com.symbol.shoppinglist.feature_category.data.repository

import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.repository.CategoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCategoryRepository : CategoriesRepository {
    val cat1 = Category("name1")
    val cat2 = Category("name1")
    val categories = mutableListOf<Category>().apply {
        add(cat1)
        add(cat2)
    }
    private val allCategories = flow<List<Category>> {
        emit(categories)
    }

    override fun getAllCategories(): Flow<List<Category>> = flow {
        emit(categories)
    }

    override suspend fun getCategory(id: Int): Category {
        return categories[id]
    }

    override suspend fun isCategoryNameTaken(name: String): Boolean {
        var categoriesWithNameFound = 0
        categories.forEach {
            if (it.name == name) categoriesWithNameFound++
        }
        return categoriesWithNameFound >= 1
    }

    override suspend fun insertCategory(category: Category) {
        categories.add(category)
    }

    override suspend fun deleteCategory(category: Category) {
        categories.remove(category)
    }

    override suspend fun insertCategories(categories: List<Category>) {
        categories.forEach { category ->
            this.categories.add(category)
        }
    }
}