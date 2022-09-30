package com.symbol.shoppinglist.database

import com.symbol.shoppinglist.data.Product
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class ProductsDataSource @Inject constructor(private val productDao: ProductDao) {
    val getAllProducts = productDao.getAllProducts()

    suspend fun insertProduct(product: Product){
        Dispatchers.IO.apply {
            productDao.insertProduct(product)
        }
    }

    suspend fun deleteProduct(product: Product){
        Dispatchers.IO.apply {
            productDao.deleteProduct(product)
        }
    }

    suspend fun updateProduct(product: Product){
        Dispatchers.IO.apply {
            productDao.updateProduct(product)
        }
    }

}