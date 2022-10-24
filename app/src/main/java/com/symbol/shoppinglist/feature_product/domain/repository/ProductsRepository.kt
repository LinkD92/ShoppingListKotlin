package com.symbol.shoppinglist.feature_product.domain.repository

import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_product.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {

    fun getAllProducts(): Flow<List<Product>>

    fun getCategoryProducts(categoryId: Int): Flow<List<Product>>

    suspend fun getProduct(id: Int): Product

    suspend fun insertProduct(product: Product)

    suspend fun doesProductExists(name: String): Int

    suspend fun deleteProduct(product: Product)

    suspend fun deleteProductById(productId: Int)
}