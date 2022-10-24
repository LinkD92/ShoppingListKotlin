package com.symbol.shoppinglist.database

import com.symbol.shoppinglist.feature_category.data.data_source.CategoriesDao
import com.symbol.shoppinglist.feature_product.data.data_source.ProductsDao
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_product.domain.model.Product
import com.symbol.shoppinglist.database.local.entities.relations.CategoryWithProducts
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

    override suspend fun addProduct(product: Product) = productsDao.addProduct(product)

    override suspend fun doesProductExists(name: String) = productsDao.doesProductExists(name)

    override suspend fun deleteProduct(product: Product) = productsDao.deleteProduct(product)

    override suspend fun deleteProductById(productId: Int) =
        productsDao.deleteProductById(productId)

    override suspend fun updateProduct(product: Product) = productsDao.updateProduct(product)

    //Categories
    override fun getAllCategories(): Flow<List<Category>> = categoriesDao.getAllCategories()

    override suspend fun getCategory(id: Int) = categoriesDao.getCategory(id)

    override suspend fun doesCategoryExists(name: String) = categoriesDao.doesCategoryExists(name)

    override suspend fun addCategory(category: Category) = categoriesDao.insertCategory(category)

    override suspend fun deleteCategory(category: Category) = categoriesDao.deleteCategory(category)

    override suspend fun updateCategory(category: Category) = categoriesDao.updateCategory(category)


    //Transactions
    override fun getCategoriesWithProducts(): Flow<List<CategoryWithProducts>> =
        productsDao.getCategoryWithProducts()
}