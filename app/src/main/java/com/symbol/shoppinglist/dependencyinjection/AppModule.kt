package com.symbol.shoppinglist.dependencyinjection

import android.app.Application
import androidx.room.Room
import com.symbol.shoppinglist.database.local.ListDao
import com.symbol.shoppinglist.database.local.ListDatabase
import com.symbol.shoppinglist.database.ListRepository
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
    ) = Room.databaseBuilder(app, ListDatabase::class.java, "products_database")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun providesProductDao(db: ListDatabase) = db.listDao()

    @Provides
    fun providesRepository(listDao: ListDao): ListRepository = ListRepository(listDao)

}