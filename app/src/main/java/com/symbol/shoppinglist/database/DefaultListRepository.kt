package com.symbol.shoppinglist.database

import androidx.lifecycle.LiveData
import com.symbol.shoppinglist.database.local.ListDao
import com.symbol.shoppinglist.database.local.entities.Category
import com.symbol.shoppinglist.database.local.entities.Product
import com.symbol.shoppinglist.database.local.entities.relations.CategoryWithProducts
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultListRepository @Inject constructor(private val listDao: ListDao) : ListRepository {
    //Products
    override fun getAllProducts(): LiveData<List<Product>> = listDao.getAllProducts()

    override suspend fun getProduct(id: Int) = listDao.getProduct(id)

    override fun getCategoryProducts(categoryId: Int): Flow<List<Product>> =
        listDao.getCategoryProducts(categoryId)

    override suspend fun addProduct(product: Product) = listDao.addProduct(product)

    override suspend fun doesProductExists(name: String) = listDao.doesProductExists(name)

    override suspend fun deleteProduct(product: Product) = listDao.deleteProduct(product)

    override suspend fun deleteProductById(productId: Int) = listDao.deleteProductById(productId)

    override suspend fun updateProduct(product: Product) = listDao.updateProduct(product)

    //Categories
    override fun getAllCategories(): LiveData<List<Category>> = listDao.getAllCategories()

    override suspend fun getCategory(id: Int) = listDao.getCategory(id)

    override fun getCategories(): Flow<List<Category>> = listDao.getCategories()

    override suspend fun doesCategoryExists(name: String) = listDao.doesCategoryExists(name)

    override suspend fun addCategory(category: Category) = listDao.addCategory(category)

    override suspend fun deleteCategory(category: Category) = listDao.deleteCategory(category)

    override suspend fun updateCategory(category: Category) = listDao.updateCategory(category)


    //Transactions
    override fun getCategoriesWithProducts(): LiveData<List<CategoryWithProducts>> =
        listDao.getCategoryWithProducts()

    override fun getCategoriesWithProductsFlow(): Flow<List<CategoryWithProducts>> =
        listDao.getCategoriesWithProductsFlow()

}