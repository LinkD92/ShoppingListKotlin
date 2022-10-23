package com.symbol.shoppinglist.dependencyinjection

import android.app.Application
import androidx.room.Room
import com.symbol.shoppinglist.DefaultDispatchers
import com.symbol.shoppinglist.DispatcherProvider
import com.symbol.shoppinglist.database.DefaultListRepository
import com.symbol.shoppinglist.database.ListRepository
import com.symbol.shoppinglist.core.data.datasource.ListRoomDatabase
import com.symbol.shoppinglist.feature_category.data.data_source.CategoriesDao
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
    ) = Room.databaseBuilder(app, ListRoomDatabase::class.java, "products_database")
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
}