package com.symbol.shoppinglist.feature_product.domain.use_case

import com.symbol.shoppinglist.feature_product.domain.model.Product
import com.symbol.shoppinglist.feature_product.domain.repository.ProductsRepository

class InsertProducts(private val repository: ProductsRepository) {
    suspend operator fun invoke(products: List<Product>) {
        repository.insertProducts(products)
    }
}