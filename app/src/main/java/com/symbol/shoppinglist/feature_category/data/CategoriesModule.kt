package com.symbol.shoppinglist.feature_category.data

import com.symbol.shoppinglist.feature_category.domain.repository.CategoriesRepository
import com.symbol.shoppinglist.feature_category.domain.use_case.*
import com.symbol.shoppinglist.feature_product.domain.repository.ProductsRepository
import com.symbol.shoppinglist.feature_settings.domain.repository.PreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CategoriesModule {

    @Provides
    @Singleton
    fun provideCategoryUseCases(
        categoriesRepository: CategoriesRepository,
        productsRepository: ProductsRepository,
        preferencesRepository: PreferencesRepository
    ): CategoryUseCases {
        return CategoryUseCases(
            getCategories = GetCategories(categoriesRepository, preferencesRepository),
            deleteCategory = DeleteCategory(categoriesRepository, productsRepository),
            insertCategory = InsertCategory(categoriesRepository),
            getCategory = GetCategory(categoriesRepository),
            reorderCategories = ReorderCategories(categoriesRepository),
            expandCategory = ExpandCategory(categoriesRepository),
        )
    }
}