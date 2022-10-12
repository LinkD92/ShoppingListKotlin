package com.symbol.shoppinglist.database

import androidx.lifecycle.LiveData
import com.symbol.shoppinglist.database.entities.Category
import com.symbol.shoppinglist.database.entities.Product
import com.symbol.shoppinglist.database.entities.relations.CategoryWithProducts
import javax.inject.Inject

class ListRepository @Inject constructor(private val listDao: ListDao) {

    fun getAllProducts(): LiveData<List<Product>> = listDao.getAllProducts()

    fun getAllCategories(): LiveData<List<Category>> = listDao.getAllCategories()

    fun getCategoriesWithProducts(): LiveData<List<CategoryWithProducts>> =
        listDao.getCategoryWithProducts()

    suspend fun addProduct(product: Product) = listDao.addProduct(product)

    suspend fun getProduct(id: Int) = listDao.getProduct(id)

    suspend fun doesProductExists(name: String) = listDao.doesProductExists(name)

    suspend fun getCategory(id: Int) = listDao.getCategory(id)

    suspend fun addCategory(category: Category) = listDao.addCategory(category)

    suspend fun deleteProduct(product: Product) = listDao.deleteProduct(product)

    suspend fun updateProduct(product: Product) = listDao.updateProduct(product)

    suspend fun updateCategory(category: Category) = listDao.updateCategory(category)
}