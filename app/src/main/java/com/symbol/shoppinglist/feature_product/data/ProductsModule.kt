package com.symbol.shoppinglist.feature_product.data

import com.symbol.shoppinglist.feature_product.domain.repository.ProductsRepository
import com.symbol.shoppinglist.feature_product.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductsModule {

    @Provides
    @Singleton
    fun provideProductUseCases(
        productsRepository: ProductsRepository,
    ): ProductUseCases {
        return ProductUseCases(
            getProducts = GetProducts(productsRepository),
            deleteProduct = DeleteProduct(productsRepository),
            insertProduct = InsertProduct(productsRepository),
            getProduct = GetProduct(productsRepository),
            getCategoryProducts = GetCategoryProducts(productsRepository),
            insertProducts = InsertProducts(productsRepository)
        )
    }
}