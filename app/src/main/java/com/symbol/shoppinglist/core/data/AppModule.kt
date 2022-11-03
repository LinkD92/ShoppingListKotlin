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
import com.symbol.shoppinglist.feature_settings.domain.PreferencesRepository
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
    @Singleton
    fun provideProtoDataStore(app: Application): DataStore<AppSettings> {
        return DataStoreFactory.create(
            serializer = AppSettingsSerializer,
            produceFile = {app.preferencesDataStoreFile(PreferencesDataStore.FILE_NAME)}
        )
    }

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
        preferencesRepository: PreferencesRepository
    ): CategoryUseCases {
        return CategoryUseCases(
            getCategories = GetCategories(categoriesRepository, preferencesRepository),
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
            insertProducts = InsertProducts(productsRepository),
            reorderCategories = ReorderCategories(categoriesRepository)
        )
    }

    @Provides
    @Singleton
    fun provideSettingsUseCases(
        productsRepository: ProductsRepository,
        categoriesRepository: CategoriesRepository,
        preferencesRepository: PreferencesRepository
    ): SettingsUseCases {
        return SettingsUseCases(
            saveDisplayProductsCategoriesOrder = SaveDisplayProductsCategoriesOrder(
                productsRepository,
                categoriesRepository,
                preferencesRepository
            ),
            getSettings = GetSettings(preferencesRepository)
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

    @Singleton
    @Binds
    abstract fun bindPreferencesRepository(
        preferencesRepositoryImpl: PreferencesRepositoryImpl
    ): PreferencesRepository
}
