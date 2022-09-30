package com.symbol.shoppinglist.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.symbol.shoppinglist.data.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM products")
    fun getAllProducts(): LiveData<List<Product>>

    @Insert
    suspend fun addProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Update
    suspend fun updateProduct(product: Product)
}