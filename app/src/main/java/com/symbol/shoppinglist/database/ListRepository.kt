package com.symbol.shoppinglist.database

import androidx.lifecycle.LiveData
import com.symbol.shoppinglist.database.local.entities.Category
import com.symbol.shoppinglist.database.local.entities.Product
import com.symbol.shoppinglist.database.local.entities.relations.CategoryWithProducts

interface ListRepository {

    fun getAllProducts(): LiveData<List<Product>>

    suspend fun getProduct(id: Int): Product

    suspend fun addProduct(product: Product)

    suspend fun doesProductExists(name: String): Int

    suspend fun deleteProduct(product: Product)

    suspend fun deleteProductById(productId: Int)

    suspend fun updateProduct(product: Product)

    //Categories
    fun getAllCategories(): LiveData<List<Category>>

    suspend fun getCategory(id: Int): Category

    suspend fun doesCategoryExists(name: String): Int

    suspend fun addCategory(category: Category)

    suspend fun deleteCategory(category: Category)

    suspend fun updateCategory(category: Category)

    //Transactions
    fun getCategoriesWithProducts(): LiveData<List<CategoryWithProducts>>
}