package com.symbol.shoppinglist.core.data

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.symbol.shoppinglist.DefaultDispatchers
import com.symbol.shoppinglist.DispatcherProvider
import com.symbol.shoppinglist.core.data.datasource.ListRoomDatabase
import com.symbol.shoppinglist.core.data.util.Database
import com.symbol.shoppinglist.core.data.util.PreferencesDataStore
import com.symbol.shoppinglist.feature_category.data.repository.CategoriesRepositoryImpl
import com.symbol.shoppinglist.feature_category.domain.repository.CategoriesRepository
import com.symbol.shoppinglist.feature_category.domain.use_case.*
import com.symbol.shoppinglist.feature_product.data.repository.ProductsRepositoryImpl
import com.symbol.shoppinglist.feature_product.domain.repository.ProductsRepository
import com.symbol.shoppinglist.feature_product.domain.use_case.*
import com.symbol.shoppinglist.feature_settings.data.PreferencesRepositoryImpl
import com.symbol.shoppinglist.feature_settings.domain.repository.PreferencesRepository
import com.symbol.shoppinglist.feature_settings.domain.model.AppSettings
import com.symbol.shoppinglist.feature_settings.domain.use_case.GetSettings
import com.symbol.shoppinglist.feature_settings.domain.use_case.SaveDisplayProductsCategoriesOrder
import com.symbol.shoppinglist.feature_settings.domain.use_case.SettingsUseCases
import com.symbol.shoppinglist.feature_settings.domain.util.AppSettingsSerializer
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

    @Singleton
    @Binds
    abstract fun bindPreferencesRepository(
        preferencesRepositoryImpl: PreferencesRepositoryImpl
    ): PreferencesRepository
}
