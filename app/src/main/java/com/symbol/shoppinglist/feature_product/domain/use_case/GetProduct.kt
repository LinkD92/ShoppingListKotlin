package com.symbol.shoppinglist.feature_product.domain.use_case

import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_product.domain.model.Product
import com.symbol.shoppinglist.feature_product.domain.repository.ProductsRepository

class GetProduct(private val repository: ProductsRepository) {

    suspend operator fun invoke(id: Int): Product {
        return repository.getProduct(id)
    }
}