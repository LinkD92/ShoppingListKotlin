package com.symbol.shoppinglist.database

import android.util.Log
import androidx.lifecycle.LiveData
import com.symbol.shoppinglist.database.entities.Product
import javax.inject.Inject

class ProductsRepository @Inject constructor(private val productDao: ProductDao) {

    fun getAllProducts(): LiveData<List<Product>> = productDao.getAllProducts()

    suspend fun addProduct(product: Product) = productDao.addProduct(product).also {
        Log.d("QWAS - addProduct:", "${product.productName}")
    }

    suspend fun deleteProduct(product: Product) = productDao.deleteProduct(product)

    suspend fun updateProduct(product: Product) = productDao.updateProduct(product)
}