package com.symbol.shoppinglist.database.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.symbol.shoppinglist.database.local.entities.Category
import com.symbol.shoppinglist.database.local.entities.Product
import com.symbol.shoppinglist.database.local.entities.relations.CategoryWithProducts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.forEach

@Dao
interface ListDao {
    //Products
    @Query("SELECT * FROM products")
    fun getAllProducts(): LiveData<List<Product>>

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProduct(id: Int): Product

    @Query("SELECT * FROM products WHERE categoryId = :categoryId ORDER BY products.isChecked DESC") //
    fun getCategoryProducts(categoryId: Int): Flow<List<Product>>

    @Query("SELECT COUNT(*) FROM products WHERE name = :name")
    suspend fun doesProductExists(name: String): Int

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("DELETE FROM products WHERE id = :productId")
    suspend fun deleteProductById(productId: Int)

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

    @Query("SELECT * FROM categories")
    fun getCategories(): Flow<List<Category>>

    //Transactions
    @Transaction
    @Query("SELECT * FROM categories")
    fun getCategoryWithProducts(): LiveData<List<CategoryWithProducts>>

    @Transaction
    @Query("SELECT * FROM categories")
    fun getCategoriesWithProductsFlow(): Flow<List<CategoryWithProducts>>

}