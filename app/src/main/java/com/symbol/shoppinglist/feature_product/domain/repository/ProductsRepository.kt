package com.symbol.shoppinglist.feature_product.domain.repository

import com.symbol.shoppinglist.feature_product.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {

    fun getAllProducts(): Flow<List<Product>>

    fun getCategoryProducts(categoryId: Int): Flow<List<Product>>

    suspend fun getProduct(id: Int): Product

    suspend fun insertProduct(product: Product)

    suspend fun insertProducts(products: List<Product>)

    suspend fun isProductNameTaken(name: String): Boolean

    suspend fun deleteProduct(product: Product)

    suspend fun deleteCategoryProducts(categoryId: Int)

    suspend fun deleteProductById(productId: Int)
}