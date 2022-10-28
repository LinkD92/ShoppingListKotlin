package com.symbol.shoppinglist.core.data

import android.app.Application
import androidx.room.Room
import com.symbol.shoppinglist.Database
import com.symbol.shoppinglist.DefaultDispatchers
import com.symbol.shoppinglist.DispatcherProvider
import com.symbol.shoppinglist.core.data.datasource.ListRoomDatabase
import com.symbol.shoppinglist.feature_category.data.repository.CategoriesRepositoryImpl
import com.symbol.shoppinglist.feature_category.domain.repository.CategoriesRepository
import com.symbol.shoppinglist.feature_category.domain.use_case.*
import com.symbol.shoppinglist.feature_product.data.repository.ProductsRepositoryImpl
import com.symbol.shoppinglist.feature_product.domain.repository.ProductsRepository
import com.symbol.shoppinglist.feature_product.domain.use_case.*
import dagger.Binds
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
    fun providesDispatchers(): DispatcherProvider = DefaultDispatchers()

    @Provides
    @Singleton
    fun provideCategoryUseCases(
        categoriesRepository: CategoriesRepository,
        productsRepository: ProductsRepository,
        dispatcherProvider: DispatcherProvider
    ): CategoryUseCases {
        return CategoryUseCases(
            getCategories = GetCategories(categoriesRepository, dispatcherProvider),
            deleteCategory = DeleteCategory(categoriesRepository, productsRepository),
            insertCategory = InsertCategory(categoriesRepository),
            getCategory = GetCategory(categoriesRepository),
        )
    }

    @Provides
    @Singleton
    fun provideProductUseCases(
        productsRepository: ProductsRepository,
        categoriesRepository: CategoriesRepository,
    ): ProductUseCases {
        return ProductUseCases(
            getProducts = GetProducts(productsRepository),
            deleteProduct = DeleteProduct(productsRepository),
            insertProduct = InsertProduct(productsRepository),
            getProduct = GetProduct(productsRepository),
            expandCategory = ExpandCategory(categoriesRepository),
            getCategoryProducts = GetCategoryProducts(productsRepository),
            insertProducts = InsertProducts(productsRepository)
        )
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ModuleBinds {

    @Singleton
    @Binds
    abstract fun bindCategoryRepository(
        categoriesRepositoryImpl: CategoriesRepositoryImpl
    ): CategoriesRepository

    @Singleton
    @Binds
    abstract fun bindProductRepository(
        productsRepositoryImpl: ProductsRepositoryImpl
    ): ProductsRepository
}
