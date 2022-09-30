package com.symbol.shoppinglist.database

import androidx.lifecycle.LiveData
import com.symbol.shoppinglist.data.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductsRepository @Inject constructor(private val productDao: ProductDao) {

    fun getAllProducts(): LiveData<List<Product>> = productDao.getAllProducts()

    suspend fun addProduct(product: Product) = productDao.addProduct(product)

    suspend fun deleteProduct(product: Product) = productDao.deleteProduct(product)

    suspend fun updateProduct(product: Product) = productDao.updateProduct(product)
}