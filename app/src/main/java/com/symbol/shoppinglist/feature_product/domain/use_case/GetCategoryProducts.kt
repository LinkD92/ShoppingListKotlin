package com.symbol.shoppinglist.feature_product.domain.use_case

import com.symbol.shoppinglist.feature_product.domain.model.Product
import com.symbol.shoppinglist.feature_product.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow

class GetCategoryProducts(private val repository: ProductsRepository) {
    operator fun invoke(categoryId: Int): Flow<List<Product>> {
        return repository.getCategoryProducts(categoryId)
    }
}