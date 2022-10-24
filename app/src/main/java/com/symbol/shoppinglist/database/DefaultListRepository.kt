package com.symbol.shoppinglist.database

import com.symbol.shoppinglist.feature_category.data.data_source.CategoriesDao
import com.symbol.shoppinglist.feature_product.data.data_source.ProductsDao
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_product.domain.model.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultListRepository @Inject constructor(
    private val productsDao: ProductsDao,
    private val categoriesDao: CategoriesDao
) : ListRepository {
    //Products
    override fun getAllProducts(): Flow<List<Product>> = productsDao.getAllProducts()

    override suspend fun getProduct(id: Int) = productsDao.getProduct(id)

    override fun getCategoryProducts(categoryId: Int): Flow<List<Product>> =
        productsDao.getCategoryProducts(categoryId)

    override suspend fun addProduct(product: Product) = productsDao.insertProduct(product)

    override suspend fun doesProductExists(name: String) = productsDao.doesProductExists(name)

    override suspend fun deleteProduct(product: Product) = productsDao.deleteProduct(product)

    override suspend fun deleteProductById(productId: Int) =
        productsDao.deleteProductById(productId)


    //Categories
    override fun getAllCategories(): Flow<List<Category>> = categoriesDao.getAllCategories()

    override suspend fun getCategory(id: Int) = categoriesDao.getCategory(id)

    override suspend fun doesCategoryExists(name: String) = categoriesDao.doesCategoryExists(name)

    override suspend fun addCategory(category: Category) = categoriesDao.insertCategory(category)

    override suspend fun deleteCategory(category: Category) = categoriesDao.deleteCategory(category)
}