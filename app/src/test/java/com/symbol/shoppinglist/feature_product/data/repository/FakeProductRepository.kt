package com.symbol.shoppinglist.feature_product.data.repository

import com.symbol.shoppinglist.feature_product.domain.model.Product
import com.symbol.shoppinglist.feature_product.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeProductRepository: ProductsRepository {
    val productsList = mutableListOf<Product>()
    private val allProducts = flow<List<Product>>{
        productsList
    }
    override fun getAllProducts(): Flow<List<Product>> {
        return allProducts
    }

    override fun getCategoryProducts(categoryId: Int): Flow<List<Product>> {
        return allProducts
    }

    override suspend fun getProduct(id: Int): Product {
        return productsList[id]
    }

    override suspend fun insertProduct(product: Product) {
        productsList.add(product)
    }

    override suspend fun insertProducts(products: List<Product>) {
        products.forEach { product ->
            productsList.add(product)
        }
    }

    override suspend fun isProductNameTaken(name: String): Boolean {
        var productsWithNameFound = 0
            productsList.forEach {
            if (it.name == name) productsWithNameFound++
        }
        return productsWithNameFound >= 1
    }

    override suspend fun deleteProduct(product: Product) {
        productsList.remove(product)
    }

    override suspend fun deleteCategoryProducts(categoryId: Int) {
        val categoryProducts = productsList.filter { product ->
            product.categoryId == categoryId
        }
        categoryProducts.forEach { product ->
            productsList.remove(product)
        }
    }

    override suspend fun deleteProductById(productId: Int) {
        val productById = productsList.find { product ->
            product.id == productId
        }
        deleteProduct(productById!!)
    }
}