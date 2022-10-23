package com.symbol.shoppinglist.core.data

import android.app.Application
import androidx.room.Room
import com.symbol.shoppinglist.Database
import com.symbol.shoppinglist.DefaultDispatchers
import com.symbol.shoppinglist.DispatcherProvider
import com.symbol.shoppinglist.core.data.datasource.ListDatabase
import com.symbol.shoppinglist.core.data.datasource.ListRoomDatabase
import com.symbol.shoppinglist.database.DefaultListRepository
import com.symbol.shoppinglist.database.ListRepository
import com.symbol.shoppinglist.feature_category.data.data_source.CategoriesDao
import com.symbol.shoppinglist.feature_category.data.repository.CategoriesRepositoryImpl
import com.symbol.shoppinglist.feature_category.domain.repository.CategoriesRepository
import com.symbol.shoppinglist.feature_category.domain.use_case.AddCategory
import com.symbol.shoppinglist.feature_category.domain.use_case.CategoryUseCases
import com.symbol.shoppinglist.feature_category.domain.use_case.DeleteCategory
import com.symbol.shoppinglist.feature_category.domain.use_case.GetCategories
import com.symbol.shoppinglist.feature_product.data.data_source.ProductsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
    ) = Room.databaseBuilder(app, ListRoomDatabase::class.java, Database.NAME)
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun providesProductDao(db: ListRoomDatabase) = db.productsDao()

    @Provides
    fun providesCategoriesDao(db: ListRoomDatabase) = db.categoriesDao()

    @Provides
    fun providesDefaultRepository(
        productsDao: ProductsDao,
        categoriesDao: CategoriesDao
    ): ListRepository = DefaultListRepository(productsDao, categoriesDao)

    @Provides
    fun providesDispatchers(): DispatcherProvider = DefaultDispatchers()

    @Provides
    @Singleton
    fun provideCategoryRepository(db: ListDatabase): CategoriesRepository {
        return CategoriesRepositoryImpl(db.categoriesDao())
    }

    @Provides
    @Singleton
    fun provideCategoryUseCases(repository: CategoriesRepository): CategoryUseCases {
        return CategoryUseCases(
            getCategories = GetCategories(repository),
            deleteCategory = DeleteCategory(repository),
            addCategory = AddCategory(repository)
        )
    }

}