package com.symbol.shoppinglist.feature_product.data.repository

import com.symbol.shoppinglist.feature_product.data.data_source.ProductsDao
import com.symbol.shoppinglist.feature_product.domain.model.Product
import com.symbol.shoppinglist.feature_product.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(private val productsDao: ProductsDao) :
    ProductsRepository {

    override fun getAllProducts(): Flow<List<Product>> = productsDao.getAllProducts()

    override suspend fun getProduct(id: Int) = productsDao.getProduct(id)

    override fun getCategoryProducts(categoryId: Int): Flow<List<Product>> =
        productsDao.getCategoryProducts(categoryId)

    override suspend fun insertProduct(product: Product) = productsDao.insertProduct(product)

    override suspend fun insertProducts(products: List<Product>) =
        productsDao.insertProducts(products)

    override suspend fun doesProductExists(name: String) = productsDao.doesProductExists(name)

    override suspend fun deleteProduct(product: Product) = productsDao.deleteProduct(product)

    override suspend fun deleteCategoryProducts(categoryId: Int) =
        productsDao.deleteCategoryProducts(categoryId)

    override suspend fun deleteProductById(productId: Int) =
        productsDao.deleteProductById(productId)
}