package com.symbol.shoppinglist.database

import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_product.domain.model.Product
import com.symbol.shoppinglist.database.local.entities.relations.CategoryWithProducts
import kotlinx.coroutines.flow.Flow

interface ListRepository {

    fun getAllProducts(): Flow<List<Product>>

    fun getCategoryProducts(categoryId: Int): Flow<List<Product>>

    suspend fun getProduct(id: Int): Product

    suspend fun addProduct(product: Product)

    suspend fun doesProductExists(name: String): Int

    suspend fun deleteProduct(product: Product)

    suspend fun deleteProductById(productId: Int)

    suspend fun updateProduct(product: Product)

    //Categories
    fun getAllCategories(): Flow<List<Category>>

    suspend fun getCategory(id: Int): Category

    suspend fun doesCategoryExists(name: String): Int

    suspend fun addCategory(category: Category)

    suspend fun deleteCategory(category: Category)

    suspend fun updateCategory(category: Category)

    //Transactions
    fun getCategoriesWithProducts(): Flow<List<CategoryWithProducts>>
}