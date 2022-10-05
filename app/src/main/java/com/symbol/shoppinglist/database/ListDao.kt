package com.symbol.shoppinglist.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.symbol.shoppinglist.database.entities.Category
import com.symbol.shoppinglist.database.entities.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM products")
    fun getAllProducts(): LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addProduct(product: Product)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addCategory(category: Category)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Update
    suspend fun updateProduct(product: Product)
}