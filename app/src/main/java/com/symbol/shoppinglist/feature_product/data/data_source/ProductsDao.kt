package com.symbol.shoppinglist.database.local.dao

import androidx.room.*
import com.symbol.shoppinglist.feature_product.domain.model.Product
import com.symbol.shoppinglist.database.local.entities.relations.CategoryWithProducts
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {
    //Products
    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProduct(id: Int): Product

    @Query("SELECT * FROM products WHERE categoryId = :categoryId ORDER BY products.isChecked DESC")
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

    //Transactions
    @Transaction
    @Query("SELECT * FROM categories")
    fun getCategoryWithProducts(): Flow<List<CategoryWithProducts>>
}