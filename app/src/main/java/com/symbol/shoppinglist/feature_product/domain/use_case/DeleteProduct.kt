package com.symbol.shoppinglist.feature_product.domain.use_case

import com.symbol.shoppinglist.feature_product.domain.model.Product
import com.symbol.shoppinglist.feature_product.domain.repository.ProductsRepository

class DeleteProduct(private val repository: ProductsRepository) {

    suspend operator fun invoke(productId: Int){
        repository.deleteProductById(productId)
    }
}