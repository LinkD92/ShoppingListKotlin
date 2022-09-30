package com.symbol.shoppinglist.dependencyinjection

import com.symbol.shoppinglist.database.ProductsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun productsRepository(productsRepository: ProductsRepository): ProductsRepository

}