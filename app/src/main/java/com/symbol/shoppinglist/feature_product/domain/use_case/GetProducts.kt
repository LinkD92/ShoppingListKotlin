package com.symbol.shoppinglist.feature_product.domain.use_case

import com.symbol.shoppinglist.core.domain.util.OrderType
import com.symbol.shoppinglist.feature_product.domain.model.Product
import com.symbol.shoppinglist.feature_product.domain.repository.ProductsRepository
import com.symbol.shoppinglist.feature_product.domain.model.ProductOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetProducts(private val repository: ProductsRepository) {

    operator fun invoke(
        productOrder: ProductOrder = ProductOrder.Name(OrderType.Ascending)
    ): Flow<List<Product>> {
        return repository.getAllProducts().map { products ->
            when(productOrder.orderType){
                is OrderType.Ascending -> products.sortedBy { it.name }
                is OrderType.Descending -> products.sortedByDescending { it.name }
            }
        }
    }
}