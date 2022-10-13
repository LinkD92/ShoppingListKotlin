package com.symbol.shoppinglist.database.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.symbol.shoppinglist.database.local.entities.Category
import com.symbol.shoppinglist.database.local.entities.Product
import com.symbol.shoppinglist.database.local.entities.relations.CategoryWithProducts

@Dao
interface ListDao {
    //Products
    @Query("SELECT * FROM products")
    fun getAllProducts(): LiveData<List<Product>>

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProduct(id: Int): Product

    @Query("SELECT COUNT(*) FROM products WHERE name = :name")
    suspend fun doesProductExists(name: String): Int

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Update
    suspend fun updateProduct(product: Product)

    //Categories
    @Query("SELECT * FROM categories")
    fun getAllCategories(): LiveData<List<Category>>

    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getCategory(id: Int): Category

    @Query("SELECT COUNT(*) FROM categories WHERE name = :name")
    suspend fun doesCategoryExists(name: String): Int

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Update
    suspend fun updateCategory(category: Category)

    //Transactions
    @Transaction
    @Query("SELECT * FROM categories")
    fun getCategoryWithProducts(): LiveData<List<CategoryWithProducts>>

}