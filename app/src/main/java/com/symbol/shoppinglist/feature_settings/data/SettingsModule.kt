package com.symbol.shoppinglist.feature_settings.data

import android.app.Application
import android.content.Intent
import androidx.core.app.ActivityCompat
import androidx.core.app.ComponentActivity
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.symbol.shoppinglist.core.data.util.PreferencesDataStore
import com.symbol.shoppinglist.feature_category.domain.repository.CategoriesRepository
import com.symbol.shoppinglist.feature_product.domain.repository.ProductsRepository
import com.symbol.shoppinglist.feature_settings.domain.model.AppSettings
import com.symbol.shoppinglist.feature_settings.domain.repository.PreferencesRepository
import com.symbol.shoppinglist.feature_settings.domain.use_case.GetSettings
import com.symbol.shoppinglist.feature_settings.domain.use_case.SaveDisplayProductsCategoriesOrder
import com.symbol.shoppinglist.feature_settings.domain.use_case.SettingsUseCases
import com.symbol.shoppinglist.feature_settings.domain.util.AppSettingsSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {
    @Provides
    @Singleton
    fun provideProtoDataStore(app: Application): DataStore<AppSettings> {
        return DataStoreFactory.create(
            serializer = AppSettingsSerializer,
            produceFile = {app.preferencesDataStoreFile(PreferencesDataStore.FILE_NAME)}

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