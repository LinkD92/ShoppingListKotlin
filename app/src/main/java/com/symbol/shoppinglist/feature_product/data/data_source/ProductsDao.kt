package com.symbol.shoppinglist.feature_product.data.data_source

import androidx.room.*
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_product.domain.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {
    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProduct(id: Int): Product

    @Query("SELECT * FROM products WHERE categoryId = :categoryId ORDER BY products.isChecked DESC , products.name ASC")
    fun getCategoryProducts(categoryId: Int): Flow<List<Product>>

    @Query("SELECT COUNT(*) FROM products WHERE name = :name")
    suspend fun doesProductExists(name: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<Product>)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("DELETE FROM products WHERE categoryId = :categoryId")
    suspend fun deleteCategoryProducts(categoryId: Int)


    @Query("DELETE FROM products WHERE id = :productId")
    suspend fun deleteProductById(productId: Int)
}