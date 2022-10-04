package com.symbol.shoppinglist.dependencyinjection

import android.app.Application
import androidx.navigation.NavController
import androidx.room.Room
import com.symbol.shoppinglist.database.ProductDao
import com.symbol.shoppinglist.database.ProductDao_Impl
import com.symbol.shoppinglist.database.ProductsDatabase
import com.symbol.shoppinglist.database.ProductsRepository
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
    ) = Room.databaseBuilder(app, ProductsDatabase::class.java, "xxx_database" )
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun providesProductDao(db: ProductsDatabase) = db.productDao()

    @Provides
    fun providesRepository(productDao: ProductDao): ProductsRepository = ProductsRepository(productDao)

}