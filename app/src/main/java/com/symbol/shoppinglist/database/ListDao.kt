package com.symbol.shoppinglist.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.symbol.shoppinglist.database.entities.Category
import com.symbol.shoppinglist.database.entities.Product
import com.symbol.shoppinglist.database.entities.relations.CategoryWithProducts

@Dao
interface ListDao {

    @Query("SELECT * FROM products")
    fun getAllProducts(): LiveData<List<Product>>

    @Query("SELECT * FROM categories")
    fun getAllCategories(): LiveData<List<Category>>

    @Transaction
    @Query("SELECT * FROM categories")
    fun getCategoryWithProducts(): LiveData<List<CategoryWithProducts>>

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProduct(id: Int): Product

    @Query("SELECT COUNT(*) FROM products WHERE productName = :name")
    suspend fun doesProductExists(name: String): Int

    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getCategory(id: Int): Category

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addProduct(product: Product)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addCategory(category: Category)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Update
    suspend fun updateProduct(product: Product)

    @Update
    suspend fun updateCategory(category: Category)
}